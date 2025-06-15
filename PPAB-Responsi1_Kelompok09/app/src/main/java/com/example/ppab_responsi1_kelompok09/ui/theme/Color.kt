package com.example.ppab_responsi1_kelompok09.ui.theme

import android.R.attr.endY
import android.R.attr.startY
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

// Base Color
val Primary = Color(0xFF007AFF)
val White = Color(0xFFFEFEFF)
val Dark = Color(0xFF2C2C2C)
val Gray = Color(0xFF999999)

val Danger = Color(0xFFEC221F)
val Success = Color(0xFF14AE5C)
val Warning = Color(0xFFE8B931)

val Primary_Accent10 = Color(0xFF007AFF).copy(0.1f)
val Dark_Accent20 = Color(0xFF2C2C2C).copy(0.2f)

// Shades primary
val Primary100 = Color(0xFFD6F2FF)
val Primary300 = Color(0xFF83DFFF)
val Primary500 = Color(0xFF1EACFF)
val Primary900 = Color(0xFF0E315D)

//Primary Gradient
val PrimaryGradient = Brush.verticalGradient(
    colors = listOf(
        Primary900,
        Primary
    ),
    startY = Float.POSITIVE_INFINITY,
    endY = 0f
)



