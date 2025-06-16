package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray

@Composable
fun KnowledgeCard (
    imageUrl: String,
    title: String,
    description: String,
    onClick: () -> Unit = {}
) {
    Column (
        modifier = Modifier
            .height(214.dp)
            .width(200.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable{ onClick() },
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalAlignment = Alignment.Start
    ) {
        AsyncImage (
            model = imageUrl,
            contentDescription = null,
            placeholder = ColorPainter(Gray),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .fillMaxWidth()
                .height(132.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        AppText(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            maxLines = 2,
            lineHeight = 16.sp
        )
        AppText(
            text = description,
            fontWeight = FontWeight.Normal,
            fontSize = 10.sp,
            maxLines = 3,
            lineHeight = 14.sp
        )
    }
}