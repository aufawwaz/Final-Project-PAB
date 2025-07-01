package com.example.ppab_responsi1_kelompok09.presentation.transaction.bill

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.data.remote.RetrofitInstance
import com.example.ppab_responsi1_kelompok09.data.repository.TransactionRepositoryImpl
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.model.TransactionItem
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetBillUseCase
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.DefaultErrorScreen
import com.example.ppab_responsi1_kelompok09.presentation.components.DefaultLoadingScreen
import com.example.ppab_responsi1_kelompok09.presentation.components.HeaderPageOnBack
import com.example.ppab_responsi1_kelompok09.presentation.components.HorizontalLine
import com.example.ppab_responsi1_kelompok09.presentation.components.shadow
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary100
import com.example.ppab_responsi1_kelompok09.ui.theme.Success
import com.example.ppab_responsi1_kelompok09.ui.theme.Warning
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import java.math.BigDecimal
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun BillDetailScreen(
    navController: NavController,
    billId : String,
    token: String?,
){
    val viewModel: BillViewModel? = if (token != null) {
        val repository = remember { TransactionRepositoryImpl(RetrofitInstance.transactionApi) }
        val useCase = remember { GetBillUseCase(repository) }
        viewModel(factory = BillViewModelFactory(useCase, token, billId))
    } else null

    if (viewModel == null) {
        DefaultErrorScreen(navController, "Detail Pembelian", "Gagal memuat detail tagihan $billId")
        return
    }

    val transaction by viewModel.transaction.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            loading -> DefaultLoadingScreen(navController, "Detail Tagihan")
            !error.isNullOrEmpty() -> DefaultErrorScreen(navController, "Detail Tagihan", error ?: "Gagal memuat detail tagihan $billId")
            else -> {
                val symbols = DecimalFormatSymbols(Locale("id", "ID")).apply {
                    groupingSeparator = '.'
                    decimalSeparator = ','
                }
                val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
                val hargaFormatter = DecimalFormat("#,###", symbols)

                val billDate = dateFormatter.format(transaction?.date ?: java.util.Date())
                val billTotal = hargaFormatter.format(transaction?.total ?: 0.0)

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White)
                        .padding(vertical = 20.dp)
                ){
                    HeaderPageOnBack(
                        onClick = { navController.popBackStack() },
                        text = "Detail Tagihan"
                    )
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .background(White)
                            .padding(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        TransactionDescriptionCard(transaction, billDate, billTotal)
                        AppText("Struk Tagihan")
                        Struk(transaction?.items, transaction, billDate, billTotal, hargaFormatter)
                        Spacer(Modifier.height(20.dp))
                    }
                }
            }
        }
    }
}

@Composable
private fun TransactionDescriptionCard(bill : Transaction.Bill?, billDate : String, billTotal : String){
    val statusColor = when (bill?.status) {
        "Lunas" -> Success.copy(0.1f)
        "Diproses" -> Warning.copy(0.1f)
        "Jatuh Tempo" -> Danger.copy(0.1f)
        else -> Gray.copy(0.1f)
    }
    val statusTextColor = when (bill?.status) {
        "Lunas" -> Success
        "Diproses" -> Warning
        "Jatuh Tempo" -> Danger
        else -> Gray
    }
    Column(
        modifier = Modifier
            .shadow(Color.Black.copy(0.1f), 16.dp, 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            AppText(bill?.id ?: "", 14.sp, FontWeight.Bold, )
            AppText(billDate, 12.sp, color = Gray)
        }
        Row (
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ){
            AppText(billTotal, 20.sp, FontWeight.SemiBold)
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(statusColor)
                    .padding(horizontal = 10.dp),
            ){
                AppText(bill?.status ?: "", 12.sp, color = statusTextColor)
            }
        }
        HorizontalLine(1f, color = Gray.copy(0.5f))
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){
            Box(modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Primary100)
                .padding(8.dp)
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_pelanggan_fill),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            AppText(bill?.customer?.nama_kontak ?: "" , fontSize = 14.sp)
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ){
            Box(modifier = Modifier
                .clip(RoundedCornerShape(12.dp))
                .background(Primary100)
                .padding(8.dp)
            ){
                Icon(
                    painter = painterResource(R.drawable.ic_saldo_fill),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            AppText(bill?.balance?.nama ?: "", fontSize = 14.sp)
        }
    }
}

@Composable
private fun Struk(items : List<TransactionItem>?, bill : Transaction.Bill?, billDate: String, billTotal: String, hargaFormatter : DecimalFormat){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(Color.Black.copy(0.1f), 16.dp, 8.dp)
            .background(White)
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppText("ScaleUp",14.sp, FontWeight.SemiBold, textAlign = TextAlign.Center)
            AppText("Kec. kecamatan, Kab. kabupaten, provinsi, Indonesia", 10.sp, color = Gray, textAlign = TextAlign.Center)
            AppText(billDate, 10.sp, color = Gray)
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Gray.copy(0.5f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            AppText("ID Transaksi", 10.sp)
            AppText(bill?.id ?: "", 10.sp)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            AppText("Pelanggan", 10.sp)
            AppText(bill?.customer?.nama_kontak ?: "", 10.sp)
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Gray.copy(0.5f)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            AppText("Deskripsi", 12.sp, FontWeight.SemiBold)
            AppText("Harga", 12.sp, FontWeight.SemiBold)
        }
        items?.forEach{
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ){
                AppText(("" + it.amount + "x " + it.product.productName), 10.sp) //nama
                AppText(hargaFormatter.format(BigDecimal(it.amount) * it.product.price), 10.sp) //harga
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            AppText("Total Pembayaran", 12.sp, FontWeight.SemiBold)
            AppText(billTotal, 12.sp, FontWeight.SemiBold)
        }
        HorizontalDivider(
            thickness = 1.dp,
            color = Color.Gray.copy(0.5f)
        )
        Column{
            AppText("Supported By", 10.sp, FontWeight.Bold, Gray)
            Row(
                horizontalArrangement = Arrangement.spacedBy(-(4.dp))
            ){
                Icon(
                    painter = painterResource(R.drawable.img_scaleup_logo),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(30.dp)
                )
                AppText("caleUp", 20.sp ,FontWeight.Bold, color = Primary)
            }
        }
    }
}