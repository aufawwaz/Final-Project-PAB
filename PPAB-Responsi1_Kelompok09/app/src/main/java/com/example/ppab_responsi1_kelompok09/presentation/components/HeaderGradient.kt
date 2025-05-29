package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.dp
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary900
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

//Component buat awalan setiap navigation kecuali homescreen

@Composable
fun HeaderGradient (height : Dp = 132.dp) {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(height)
            .dropShadow200(0.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Primary900, Primary),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                ))
    ) {}
}