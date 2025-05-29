package com.example.ppab_responsi1_kelompok09.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

//data class ScaleUpColors(
//    val primary: Color,
//    val background: Color,
//    val text: Color,
//    val success: Color,
//    val danger: Color,
//    val warning: Color,
//    val gray: Color,
//    val dark: Color
//)

private val ScaleUpLightTheme = lightColorScheme(
    primary = Primary,
    onPrimary = White,
    background = White,
    onBackground = Dark,
    error = Warning
)

private val ScaleUpDarkTheme = darkColorScheme(
    primary = Primary,
    onPrimary = White,
    background = Dark,
    onBackground = White,
    error = Warning
)

//data class ScaleUpPadding(
//    val padding
//)

//owkedowkdowdk


@Composable
fun ScaleUpTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> ScaleUpLightTheme
        else -> ScaleUpDarkTheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
