package com.example.ppab_responsi1_kelompok09.presentation.transaction.sale

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.model.TransactionItem
import com.example.ppab_responsi1_kelompok09.domain.repository.ProductRepository
import com.example.ppab_responsi1_kelompok09.domain.repository.TransactionItemRepository
import com.example.ppab_responsi1_kelompok09.domain.repository.TransactionRepository
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.BottomSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomButton
import com.example.ppab_responsi1_kelompok09.presentation.components.HeaderPageOnBack
import com.example.ppab_responsi1_kelompok09.presentation.components.HorizontalLine
import com.example.ppab_responsi1_kelompok09.presentation.components.shadow
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary100
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun SaleDetailScreen(
    navController: NavController,
    saleId: String
) {
    val transaction = TransactionRepository.getTransactionById(saleId)
    if (transaction !is Transaction.Sell) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AppText("Penjualan dengan ID $saleId tidak ditemukan", 14.sp, color = Color.Red)
            Spacer(Modifier.height(8.dp))
            CustomButton(onClick = { navController.popBackStack() }, "Kembali")
        }
        return
    }
    val sale = transaction

    val symbols = DecimalFormatSymbols(Locale("id", "ID")).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))
    val hargaFormatter = DecimalFormat("#,###", symbols)

    val saleDate = dateFormatter.format(sale.date)
    val saleTotal = hargaFormatter.format(sale.total)
    val items = TransactionItemRepository.getTransactionItems(sale.id)

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 20.dp)
    ){
        HeaderPageOnBack(
            onClick = { navController.popBackStack() },
            text = "Detail Penjualan"
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .background(White)
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TransactionDescriptionCard(sale, saleDate, saleTotal)
            AppText("Struk Penjualan")
            Struk(items, sale, saleDate, saleTotal, hargaFormatter)
            Spacer(Modifier.height(20.dp))
        }
    }
}

@Composable
private fun TransactionDescriptionCard(sale : Transaction.Sell, saleDate : String, saleTotal : String){
    Column(
        modifier = Modifier
            .shadow(Color.Black.copy(0.1f), 16.dp, 8.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppText(sale.id, 14.sp, FontWeight.Bold)
            AppText(saleDate, 12.sp, color = Gray)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppText(saleTotal, 20.sp, FontWeight.SemiBold)
            // Tampilkan paymentMethod:
            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(100.dp))
                    .background(Primary.copy(0.1f))
                    .padding(horizontal = 14.dp)
            ) {
                AppText(sale.paymentMethod, 12.sp, color = Primary)
            }
        }
        HorizontalLine(1f, color = Gray.copy(0.5f))
        // Kontak pelanggan:
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Primary100)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_pelanggan_fill),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            AppText(sale.customer.nama_kontak, fontSize = 14.sp)
        }
        // Saldo:
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(12.dp))
                    .background(Primary100)
                    .padding(8.dp)
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_saldo_fill),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(24.dp)
                )
            }
            AppText(sale.balance.nama, fontSize = 14.sp)
        }
    }
}

@Composable
private fun Struk(items : List<TransactionItem>, sale : Transaction.Sell, saleDate: String, saleTotal: String, hargaFormatter: DecimalFormat){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(Color.Black.copy(0.1f), 16.dp, 8.dp)
            .background(White)
            .padding(32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            AppText("ScaleUp", 14.sp, FontWeight.SemiBold, textAlign = TextAlign.Center)
            AppText("Kec. kecamatan, Kab. kabupaten, provinsi, Indonesia", 10.sp, color = Gray, textAlign = TextAlign.Center)
            AppText(saleDate, 10.sp, color = Gray)
        }
        HorizontalDivider(thickness = 1.dp, color = Color.Gray.copy(0.5f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText("ID Transaksi", 10.sp)
            AppText(sale.id, 10.sp)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText("Pelanggan", 10.sp)
            AppText(sale.customer.nama_kontak, 10.sp)
        }
        HorizontalDivider(thickness = 1.dp, color = Color.Gray.copy(0.5f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText("Deskripsi", 12.sp, FontWeight.SemiBold)
            AppText("Harga", 12.sp, FontWeight.SemiBold)
        }
        items.forEach {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
//                AppText(ProductRepository.getProductById(it.productId)?.productName ?: "Unknown", 10.sp)
//                AppText(hargaFormatter.format(ProductRepository.getProductById(it.productId)?.price) ?: "?", 10.sp)
            }
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText("Total Pembayaran", 12.sp, FontWeight.SemiBold)
            AppText(saleTotal, 12.sp, FontWeight.SemiBold)
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            AppText("Metode Pembayaran", 10.sp)
            AppText(sale.paymentMethod, 10.sp)
        }
        HorizontalDivider(thickness = 1.dp, color = Color.Gray.copy(0.5f))
        Column {
            AppText("Supported By", 10.sp, FontWeight.Bold, Gray)
            Row(horizontalArrangement = Arrangement.spacedBy(-(6.dp))) {
                Icon(
                    painter = painterResource(R.drawable.img_scaleup_logo),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(30.dp)
                )
                AppText("caleUp", 20.sp, FontWeight.Bold, color = Primary)
            }
        }
    }
}