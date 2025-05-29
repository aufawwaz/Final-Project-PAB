package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PageHeader(pagetitle : String, title : String, iconRes : Int, description : String){
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(162.dp)
    ) {
        HeaderGradient()
        PageTextHeader(
            text = pagetitle,
            modifier = Modifier.offset(x = 16.dp, y = 56.dp)
        )
        HeaderBox(
            iconRes = iconRes,
            title = title,
            description = description,
            modifier = Modifier
                .offset(y = 102.dp)
        )
    }
}