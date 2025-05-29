package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary

//Component buat icon (primary) dengan background tonal

@Composable
fun TonalIcon (
    isClickable : Boolean = false,
    onClick : () -> Unit = {},
    iconHeight : Dp,
    iconRes : Int,
    iconBackground : Color = Primary,
    boxSize : Dp
) {
    Box(
        modifier = Modifier
            .size(boxSize)
            .clip(RoundedCornerShape(8.dp))
            .clickable(
                enabled = isClickable,
                onClick = onClick
            )
            .background(iconBackground.copy(0.1f))
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            modifier = Modifier
                .height(iconHeight)
                .align(Alignment.Center),
            tint = iconBackground
        )
    }
}