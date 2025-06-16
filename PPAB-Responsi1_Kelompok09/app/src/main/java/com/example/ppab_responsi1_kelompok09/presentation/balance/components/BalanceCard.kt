package com.example.ppab_responsi1_kelompok09.presentation.balance.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.Balance
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.TonalIcon
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.presentation.components.formatToCurrency
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun BalanceCard(
    balance: Balance,
    onClick: () -> Unit = {}
) {
    // icon behaviour
    var iconRes = if (balance.jenis == "Bank") {
        R.drawable.ic_bank
    } else {
        R.drawable.ic_pendapatan
    }

    Row (
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .dropShadow200(8.dp)
            .fillMaxWidth()
            .height(64.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable { onClick() }
            .background(White)
            .padding(horizontal = 16.dp)
    ) { Row {
            TonalIcon(
                iconRes = iconRes,
                iconHeight = 28.dp,
                iconBackground = Primary,
                boxSize = 36.dp
            )
            Spacer(Modifier.width(16.dp))
            Column (
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AppText(
                    text = balance.nama,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp,
                    color = Gray
                )
                AppText(
                    text = formatToCurrency(balance.saldo),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }
        }
        Icon(
            painter = painterResource(R.drawable.ic_next),
            contentDescription = null,
            modifier = Modifier.size(20.dp)
        )
    }
}