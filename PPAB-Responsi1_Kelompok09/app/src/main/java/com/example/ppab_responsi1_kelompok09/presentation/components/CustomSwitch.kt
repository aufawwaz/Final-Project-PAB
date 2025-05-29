package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun CustomSwitch(
    checked: Boolean,
    onCheckedChange: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    height: Dp = 44.dp,
    text1: String = "text1",
    text2: String = "text2"
) {
    BoxWithConstraints(
        modifier = modifier
            .height(height)
            .fillMaxWidth()
            .border(1.dp, Gray.copy(0.3f), RoundedCornerShape(16.dp))
            .clip(RoundedCornerShape(16.dp))
            .clickable { onCheckedChange(!checked) }
    ) {
        val handleWidth = maxWidth / 2
        val alignment by animateDpAsState(
            if (checked) maxWidth - handleWidth else 0.dp,
            label = "switch_handle_position"
        )

        Box(
            modifier = Modifier
                .offset(x = alignment)
                .width(handleWidth)
                .fillMaxHeight()
                .clip(RoundedCornerShape(16.dp))
                .background(Primary),
            contentAlignment = Alignment.Center
        ) {}

        Row (
            horizontalArrangement = Arrangement.SpaceAround,
            modifier = Modifier
                .fillMaxSize()
                .zIndex(10f),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val text1Color = if (checked) Dark else White
            AppText(text1, fontSize = 12.sp, color = text1Color)

            val text2Color = if (checked) White else Dark
            AppText(text2, fontSize = 12.sp, color = text2Color)
        }
    }
}