package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray

@Composable
fun HorizontalLine(length : Float = 0.8f, thick : Dp = 1.dp, color : Color = Gray){
    Box(
        modifier = Modifier
            .fillMaxWidth(length)
            .height(thick)
            .background(color)
    )
}