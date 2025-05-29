package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

// Component buat satu deret profil

@Composable
fun ProfileContainer (
    icon : Int,
    text : String,
    isLogin : Boolean = false
) {
    Row (
        modifier = Modifier
            .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!isLogin) {
                Icon(
                    painter = painterResource(icon),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp)
                        .clip(CircleShape)
                )
            } else {
                Image(
                    painter = painterResource(icon),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp)
                        .clip(CircleShape)
                )
            }
            AppText(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (isLogin) {
                Box (
                    modifier = Modifier
                        .clip(RoundedCornerShape(8.dp))
                        .background(White)
                ) {
                    AppText(
                        modifier = Modifier
                            .padding(vertical = 4.dp, horizontal = 8.dp)
                            .align(Alignment.Center),
                        text = "BASIC",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Medium,
                        color = Primary
                    )
                }
            }
            Box (
                contentAlignment = Alignment.Center
            ) {
                Box (
                    modifier = Modifier
                        .background(Danger)
                        .clip(CircleShape)
                        .border(
                            width = 1.dp,
                            color = White
                        )
                )
                Icon(
                    painter = painterResource(R.drawable.ic_notifikasi),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}