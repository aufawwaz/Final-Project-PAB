package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.Success
import com.example.ppab_responsi1_kelompok09.ui.theme.Warning
import java.text.SimpleDateFormat
import java.util.*

fun formatDate(date: Date): String {
    val sdf = SimpleDateFormat("dd MMM yyyy", Locale.getDefault())
    return sdf.format(date)
}

fun getStatusColor(status : String) : Color {
    return when (status.lowercase()) {
        "lunas" -> Success
        "diproses" -> Warning
        "jatuh tempo" -> Danger
        else -> Primary
    }
}

@Composable
fun TransactionCard (
    transaction: Transaction,
    modifier : Modifier = Modifier,
    isIdInCard: Boolean = true,
    onClick: () -> Unit = {}
) {
    val clickableModifier = if (isIdInCard) modifier.fillMaxWidth().clickable { onClick() } else modifier.fillMaxWidth()
    Column (
        modifier = clickableModifier,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        when (transaction) {
            is Transaction.Sell -> SellCard(transaction, isIdInCard, onClick)
            is Transaction.Purchase -> PurchaseCard(transaction, isIdInCard, onClick)
            is Transaction.Bill -> BillCard(transaction, isIdInCard, onClick)
        }
    }
}

@Composable
fun SellCard(
    data: Transaction.Sell,
    isIdVisible: Boolean,
    onClick: () -> Unit = {}
) {
    val clickableModifier = if (isIdVisible) Modifier.fillMaxWidth().clickable { onClick() } else Modifier.fillMaxWidth()
    Column(
        modifier = clickableModifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            AppText(
                text = data.customer.nama_kontak,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            AppText(
                text = formatDate(data.date),
                fontWeight = FontWeight.Light,
                fontSize = 10.sp
            )
        }
        if (isIdVisible) {
            AppText(
                text = data.id,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TonalText(
                text = data.paymentMethod,
                textColor = Primary
            )
            AppText(
                text = formatToCurrency(data.total),
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Success
            )
        }
    }
}

@Composable
fun PurchaseCard(
    data: Transaction.Purchase,
    isIdVisible: Boolean,
    onClick: () -> Unit = {}
) {
    val arrangement = if (isIdVisible) Arrangement.SpaceBetween else Arrangement.End
    val clickableModifier = if (isIdVisible) Modifier.fillMaxWidth().clickable { onClick() } else Modifier.fillMaxWidth()
    Column(
        modifier = clickableModifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top
        ) {
            AppText(
                text = data.supplier.nama_kontak,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            AppText(
                text = formatDate(data.date),
                fontWeight = FontWeight.Light,
                fontSize = 10.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = arrangement,
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isIdVisible) {
                AppText(
                    text = data.id,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
            }
            AppText(
                text = formatToCurrency(data.total),
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Danger
            )
        }
    }
}

@Composable
fun BillCard(
    data: Transaction.Bill,
    isIdVisible: Boolean,
    onClick: () -> Unit = {}
) {
    val statusColor = getStatusColor(data.status)
    val clickableModifier = if (isIdVisible) Modifier.fillMaxWidth().clickable { onClick() } else Modifier.fillMaxWidth()
    Column(
        modifier = clickableModifier.padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.Top,
        ) {
            AppText(
                text = data.customer.nama_kontak,
                fontWeight = FontWeight.SemiBold,
                fontSize = 14.sp
            )
            AppText(
                text = formatDate(data.date),
                fontWeight = FontWeight.Light,
                fontSize = 10.sp
            )
        }
        if (isIdVisible) {
            AppText(
                text = data.id,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TonalText(
                text = data.status,
                textColor = statusColor
            )
            AppText(
                text = formatToCurrency(data.total),
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = statusColor
            )
        }
    }
}
