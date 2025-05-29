package com.example.ppab_responsi1_kelompok09.presentation.onboard.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.onboard.OnboardItem
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun OnboardingPage(
    modifier : Modifier = Modifier,
    pages : OnboardItem
) {
    Box(
        modifier = modifier
           .fillMaxSize()
    ) {
        Column (
            modifier = Modifier
                .width(270.dp)
                .align(Alignment.TopCenter)
                .offset(y = 132.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            AppText(
                text = pages.title,
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )
            AppText(
                text = pages.description,
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp,
                textAlign = TextAlign.Center,
                lineHeight = 16.sp
            )
        }
        Image(
            painter = painterResource(pages.image),
            contentDescription = null,
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .offset(y = -(232.dp))
        )
    }
}