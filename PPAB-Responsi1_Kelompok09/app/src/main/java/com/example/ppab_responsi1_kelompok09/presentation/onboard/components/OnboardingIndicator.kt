package com.example.ppab_responsi1_kelompok09.presentation.onboard.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary

@Composable
fun OnboardingIndicator (
    modifier: Modifier = Modifier,
    pageSize: Int,
    selectedPage: Int,
    selectedColor: Color = Primary,
    unselectedColor: Color = Gray.copy(0.5f)
) {
    Row (
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        repeat(pageSize) { i ->
            Box(
                modifier = Modifier
                    .height(8.dp)
                    .width(if (i == selectedPage) 24.dp else 8.dp)
                    .clip(CircleShape)
                    .background(color = if (i == selectedPage) selectedColor else unselectedColor)
            )
        }
    }
}