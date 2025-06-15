package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark

@Composable
fun DrawLine(
    width: Dp = 1.dp
) {
    Box(Modifier.fillMaxWidth().height(width).background(Dark.copy(0.2f)))
}