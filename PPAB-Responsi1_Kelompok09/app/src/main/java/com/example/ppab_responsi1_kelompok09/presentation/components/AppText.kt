package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.ui.graphics.Color
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Poppins
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import org.w3c.dom.Text

@Composable
fun AppText(
    text: String,
    fontSize: TextUnit = 12.sp,
    fontWeight: FontWeight = FontWeight.Normal,
    color: Color = Dark,
    modifier: Modifier = Modifier,
    textAlign : TextAlign = TextAlign.Start,
    lineHeight : TextUnit = fontSize,
    isClickable: Boolean = false,
    maxLines : Int = Int.MAX_VALUE,
    overflow: TextOverflow = TextOverflow.Ellipsis,
    onClick : () -> Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }

    val finalModifier = if (isClickable) {
        modifier.clickable(
            indication = null,
            interactionSource = interactionSource,
            onClick = onClick
        )
    } else {
        modifier
    }

    Text(
        text = text,
        fontSize = fontSize,
        fontWeight = fontWeight,
        letterSpacing = 0.sp,
        textAlign = textAlign,
        lineHeight = lineHeight,
        fontFamily = Poppins,
        color = color,
        maxLines = maxLines,
        overflow = overflow,
        modifier = finalModifier
    )
}

@Composable
fun HomeTextHeader(
    text: String,
    modifier: Modifier = Modifier
) {
    AppText(
        text = text,
        fontSize = 14.sp,
        fontWeight = FontWeight.Bold,
        modifier = modifier
    )
}

@Composable
fun PageTextHeader (
    text: String,
    modifier: Modifier = Modifier
) {
    AppText(
        text = text,
        fontSize = 20.sp,
        fontWeight = FontWeight.SemiBold,
        color = White,
        modifier = modifier
    )
}




















