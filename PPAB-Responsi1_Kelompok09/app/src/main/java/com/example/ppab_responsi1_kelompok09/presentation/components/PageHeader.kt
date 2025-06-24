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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

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

@Composable
fun PageHeaderLoadingState(title: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(162.dp)
    ) {
        HeaderGradient()

        // Title (Contoh: "Kontak")
        AppText(
            text = title,
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            color = White,
            modifier = Modifier.offset(x = 16.dp, y = 56.dp)
        )

        // Shimmer card
        Row(
            modifier = Modifier
                .offset(y = 102.dp)
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
            // Placeholder ikon
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .placeholder(
                        visible = true,
                        color = Color.LightGray,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color(0xFFBBBBBB)
                        )
                    )
            )

            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                // Placeholder judul (e.g., "Total Kontak")
                Box(
                    modifier = Modifier
                        .height(12.dp)
                        .width(60.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .placeholder(
                            visible = true,
                            color = Color.LightGray,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color(0xFFBBBBBB)
                            )
                        )
                )

                // Placeholder angka (e.g., "58 Kontak")
                Box(
                    modifier = Modifier
                        .height(20.dp)
                        .width(100.dp)
                        .clip(RoundedCornerShape(4.dp))
                        .placeholder(
                            visible = true,
                            color = Color.LightGray,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color(0xFFBBBBBB)
                            )
                        )
                )
            }
        }
    }
}
