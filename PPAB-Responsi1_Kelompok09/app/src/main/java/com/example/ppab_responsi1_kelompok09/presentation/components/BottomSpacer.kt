package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BottomSpacer (
    height: Dp = 130.dp
) {
    Spacer(Modifier.height(height))
}