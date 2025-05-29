package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary

@Composable
fun TonalText (
    isClickable : Boolean = false,
    onClick : () -> Unit = {},
    text : String,
    textColor : Color = Primary,
    paddingSize : Dp = 8.dp
) {
    Box(
        modifier = Modifier
            .fillMaxHeight()
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                enabled = isClickable,
                onClick = onClick
            )
            .background(textColor.copy(0.1f))
            .padding(horizontal = paddingSize)
    ) {
        AppText(
            text = text,
            fontWeight = FontWeight.Normal,
            fontSize = 12.sp,
            color = textColor
        )
    }
}