package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun PageHeader(pagetitle : String, title : String, iconRes : Int, description : String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(162.dp)
    ) {
        HeaderGradient()
        PageTextHeader(
            text = pagetitle,
            modifier = Modifier.offset(x = 16.dp, y = 56.dp)
        )
        HeaderBox(
            iconRes = iconRes,
            title = title,
            description = description,
            modifier = Modifier
                .offset(y = 102.dp)
        )
    }
}

@Composable
fun HeaderBox (
    iconRes : Int,
    title : String,
    description : String,
    modifier: Modifier = Modifier
) {
    Row (
        modifier = modifier
            .fillMaxWidth()
            .height(60.dp)
            .padding(horizontal = 16.dp)
            .dropShadow200(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TonalIcon(
            iconRes = iconRes,
            iconHeight = 24.dp,
            boxSize = 40.dp
        )
        Column {
            AppText(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal
            )
            AppText(
                text = description,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = Primary
            )
        }
    }
}