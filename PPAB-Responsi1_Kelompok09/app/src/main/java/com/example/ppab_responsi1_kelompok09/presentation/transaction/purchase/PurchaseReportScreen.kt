package com.example.ppab_responsi1_kelompok09.presentation.transaction.sale

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.repository.TransactionRepository
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.BottomSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.DateFilter
import com.example.ppab_responsi1_kelompok09.presentation.components.DateFilterOverlay
import com.example.ppab_responsi1_kelompok09.presentation.components.HeaderPageOnBack
import com.example.ppab_responsi1_kelompok09.presentation.components.PurchaseCard
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.presentation.components.getDateRangeValue
import com.example.ppab_responsi1_kelompok09.presentation.transaction.component.CardTotalTransaction
import com.example.ppab_responsi1_kelompok09.presentation.transaction.component.DateFilterButton
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import java.time.ZoneId

@Composable
fun PurchaseReportScreen(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    val transaction = TransactionRepository.getAllTransaction()

    // Buat filter tanggal
    var showOverlay by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(DateFilter.TODAY) }

    // Pilih penjualan aja
    val (startDate, endDate) = getDateRangeValue(selectedFilter)
    val purchaseList = transaction
        .filterIsInstance<Transaction.Purchase>()
        .filter {
            val localDate = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            localDate in startDate..endDate
        }

    // Buat kartu transaksi total penjualan
    var totalTransaction = purchaseList.size
    var totalPrice = purchaseList.sumOf { it.total.toLong() }

    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 20.dp)
    ) {
        Column (
            modifier = Modifier.fillMaxWidth()
        ) {
            HeaderPageOnBack(
                onClick = { navController.popBackStack() },
                text = "Laporan Pembelian"
            )
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
            ) {
                Spacer(Modifier.height(1.dp))
                DateFilterButton(
                    onClickShowOverlay = { showOverlay = true },
                    selectedFilter = selectedFilter
                )
                CardTotalTransaction(
                    text = "Pembelian",
                    icon = R.drawable.ic_pembelian_fill,
                    totalTransaction = totalTransaction,
                    totalPrice = totalPrice,
                    priceColor = Danger
                )
                Box(
                    contentAlignment = Alignment.CenterStart,
                    modifier = Modifier.height(40.dp)
                ) {
                    AppText(
                        text = "Daftar Pembelian",
                        fontWeight = FontWeight.Medium,
                        fontSize = 16.sp
                    )
                }
                ListPembelian(pembelian = purchaseList, navController)
                BottomSpacer(40.dp)
            }
        }
        if (showOverlay) {
            DateFilterOverlay(
                onDismiss = { showOverlay = false },
                onSelected = { selected ->
                    selectedFilter = selected
                }
            )
        }
    }
}

@Composable
fun ListPembelian(
    pembelian: List<Transaction.Purchase>,
    navController: NavController
) {
    Column(
        modifier = Modifier
            .dropShadow200(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
    ) {
        pembelian.forEachIndexed { index, transaksi ->
            PurchaseCard(
                data = transaksi,
                isIdVisible = true,
                onClick = { navController.navigate("pembelian_detail/" + transaksi.id ) }
            )
            if (index != pembelian.lastIndex) {
                Box(
                    Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(Dark.copy(0.2f))
                )
            }
        }
    }
}