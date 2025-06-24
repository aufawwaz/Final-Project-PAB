package com.example.ppab_responsi1_kelompok09.presentation.balance

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.repository.BalanceRepository
import com.example.ppab_responsi1_kelompok09.domain.repository.TransactionRepository
//import com.example.ppab_responsi1_kelompok09.domain.repository.TransactionRepository
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.HeaderPageOnBack
import com.example.ppab_responsi1_kelompok09.presentation.components.HorizontalLine
import com.example.ppab_responsi1_kelompok09.presentation.components.TonalIcon
import com.example.ppab_responsi1_kelompok09.presentation.components.TransactionCard
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.presentation.components.formatToCurrency
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

@Composable
fun BalanceDetailScreen(
    navController: NavController,
    balanceId: String
) {
    val balance = BalanceRepository.getBalanceById(balanceId) ?: return

    val transactions = TransactionRepository.getAllTransaction().filter {
        when(it){
            is Transaction.Sell -> it.balance.id == balanceId
            is Transaction.Purchase -> it.balance.id == balanceId
            is Transaction.Bill -> it.balance.id == balanceId
        }
    }

    Column (
        modifier = Modifier.fillMaxWidth().padding(vertical = 20.dp)
    ){
        HeaderPageOnBack(
            onClick = { navController.popBackStack() },
            text = "Detail Saldo"
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ){
            BalanceHeader(balance.nama, total = balance.saldo.toInt())
            if(transactions.isNotEmpty()) {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceAround
                ){
                    AppText("Riwayat Transaksi", 14.sp, fontWeight = FontWeight.SemiBold)
                    HorizontalLine(0.9f, color = Gray.copy(0.5f))
                }
                TransactionList(transactions, navController)
            }
            else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.8f),
                    contentAlignment = Alignment.Center
                ) {
                    AppText(
                        text = "Tidak ada transaksi untuk saldo ini",
                        fontSize = 16.sp,
                        color = Gray
                    )
                }
            }
        }
    }
}

@Composable
private fun BalanceHeader(
    saldoName : String,
    total: Int
){
    Row (
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .dropShadow200(8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .padding(16.dp)
    ) {
        TonalIcon(
            iconRes = R.drawable.ic_saldo_fill,
            iconHeight = 32.dp,
            iconBackground = Primary,
            boxSize = 48.dp
        )
        Spacer(Modifier.width(16.dp))
        Column (
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AppText(
                text = saldoName,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                color = Gray
            )
            AppText(
                text = formatToCurrency(total),
                fontSize = 20.sp,
                fontWeight = FontWeight.SemiBold,
                color = Primary
            )
        }
    }
}

@Composable
private fun TransactionList(
    transactions: List<Transaction>,
    navController: NavController,
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        items(transactions.size) { index ->
            val transaction = transactions[index]
            val id = when(transaction){
                is Transaction.Sell -> transaction.balance.id
                is Transaction.Purchase -> transaction.balance.id
                is Transaction.Bill -> transaction.balance.id
            }
            val transactionDetailRoute = when(transaction){
                is Transaction.Sell -> "penjualan_detail/${transaction.id}"
                is Transaction.Purchase -> "pembelian_detail/${transaction.id}"
                is Transaction.Bill -> "tagihan_detail/${transaction.id}"
            }
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .dropShadow200(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(White)
                    .clickable { navController.navigate(transactionDetailRoute) }
                    .padding(horizontal = 8.dp, vertical = 8.dp)
            ){
                TransactionCard(
                    transaction = transaction,
                    isIdInCard = false
                )
            }
        }
        item{ Spacer(Modifier.height(30.dp)) }
    }
}