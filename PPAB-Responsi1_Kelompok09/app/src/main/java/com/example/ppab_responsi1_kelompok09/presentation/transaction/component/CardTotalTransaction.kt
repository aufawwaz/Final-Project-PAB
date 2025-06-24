package com.example.ppab_responsi1_kelompok09.presentation.transaction.component

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.presentation.components.formatToCurrency
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun CardTotalTransaction (
    text: String = "",
    icon: Int = 0,
    totalTransaction: Int = 0,
    totalPrice: Long = 0,
    priceColor: Color
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow200(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(16.dp)
    ) {
        AppText(
            text = text,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp
        )
        Box(Modifier.height(1.dp).fillMaxWidth().background(Dark.copy(0.2f)))
        Column (
            verticalArrangement = Arrangement.spacedBy(24.dp),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier.padding(12.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(icon),
                        contentDescription = null,
                        modifier = Modifier.size(32.dp),
                        tint = Primary
                    )
                }
                AppText(
                    text = "$totalTransaction $text",
                    color = Primary,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Dark.copy(0.04f))
                    .padding(vertical = 8.dp, horizontal = 16.dp)
            ) {
                AppText(
                    text = "Total",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
                AppText(
                    text = formatToCurrency(totalPrice),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = priceColor
                )
            }
        }
    }
}