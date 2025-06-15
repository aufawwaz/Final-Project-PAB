package com.example.ppab_responsi1_kelompok09.presentation.balance

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.Balance
import com.example.ppab_responsi1_kelompok09.domain.repository.BalanceRepository
import com.example.ppab_responsi1_kelompok09.presentation.balance.components.BalanceCard
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.BottomSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.HeaderPageOnBack
import com.example.ppab_responsi1_kelompok09.presentation.components.TonalIcon
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.presentation.components.formatToCurrency
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun BalanceScreen (
    navController: NavController
) {
    // List Balance (Dummy)
    val balanceList = BalanceRepository.getAllBalance()

    // Total saldo
    var totalBalance = balanceList.sumOf { it.saldo.toLong() }

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
                text = "Kartu dan Saldo"
            )
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(Modifier.height(1.dp))
                TotalBalance(total = totalBalance)
                ListSaldo(balanceList, navController)
                BottomSpacer(40.dp)
            }
        }
    }
}

@Composable
private fun TotalBalance(
    total: Long
) {
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
                text = "Total saldo",
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
private fun ListSaldo(
    balanceList : List<Balance>,
    navController: NavController
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier.fillMaxWidth()
    ) {
        AppText(
            text = "Semua kartu",
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        )
        balanceList.forEach { balance ->
            BalanceCard(
                balance,
                onClick = { navController.navigate("balance_detail/${balance.id}") }
            )
        }
    }
}