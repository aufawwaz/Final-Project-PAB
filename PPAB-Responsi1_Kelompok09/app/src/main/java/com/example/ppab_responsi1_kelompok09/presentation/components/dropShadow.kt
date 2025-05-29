package com.example.ppab_responsi1_kelompok09.presentation.components

import android.graphics.BlurMaskFilter
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Paint
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Component shadow yang bisa dikustomisasi

fun Modifier.shadow(
    color: Color = Color.Black,
    borderRadius: Dp = 0.dp,
    blurRadius: Dp = 0.dp,
    offsetY: Dp = 0.dp,
    offsetX: Dp = 0.dp,
    spread: Dp = 0f.dp,
    modifier: Modifier = Modifier
) = this.then(
    modifier.drawBehind {
        this.drawIntoCanvas {
            val paint = Paint()
            val frameworkPaint = paint.asFrameworkPaint()
            val spreadPixel = spread.toPx()
            val leftPixel = (0f - spreadPixel) + offsetX.toPx()
            val topPixel = (0f - spreadPixel) + offsetY.toPx()
            val rightPixel = (this.size.width + spreadPixel)
            val bottomPixel = (this.size.height + spreadPixel)

            if (blurRadius != 0.dp) {
                frameworkPaint.maskFilter =
                    (BlurMaskFilter(blurRadius.toPx(), BlurMaskFilter.Blur.NORMAL))
            }

            frameworkPaint.color = color.toArgb()
            it.drawRoundRect(
                left = leftPixel,
                top = topPixel,
                right = rightPixel,
                bottom = bottomPixel,
                radiusX = borderRadius.toPx(),
                radiusY = borderRadius.toPx(),
                paint
            )
        }
    }
)

// Pake ini kalo mau ngasih shadow, sesuaikan border radius dengan yang ada pada parent component

fun Modifier.dropShadow200(borderRadius: Dp): Modifier =
    this
        .shadow(
            color = Color(0xFF0C0C0D).copy(alpha = 0.1f),
            borderRadius = borderRadius,
            blurRadius = 4.dp,
            offsetX = 0.dp,
            offsetY = 1.dp,
            spread = 0.dp
        )
        .shadow(
            color = Color(0xFF0C0C0D).copy(alpha = 0.05f),
            borderRadius = borderRadius,
            blurRadius = 4.dp,
            offsetX = 0.dp,
            offsetY = 1.dp,
            spread = 0.dp
        )