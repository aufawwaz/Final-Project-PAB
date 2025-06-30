package com.example.ppab_responsi1_kelompok09.presentation.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

// Component buat satu deret profil

@Composable
fun ProfileContainer(
    imageUrl: String? = null, // Tambahkan imageUrl untuk foto profil dari API
    placeholder: Int = R.drawable.img_profile_picture, // Placeholder jika imageUrl null
    text: String,
    isLogin: Boolean = false,
    onClick: () -> Unit = {}
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = { onClick() }
            ),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            if (!isLogin) {
                Icon(
                    painter = painterResource(placeholder),
                    contentDescription = null,
                    tint = White,
                    modifier = Modifier
                        .width(28.dp)
                        .height(28.dp)
                        .clip(CircleShape)
                )
            } else {
                if (imageUrl != null && imageUrl.isNotBlank()) {
                    AsyncImage(
                        model = imageUrl,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(28.dp)
                            .height(28.dp)
                            .clip(CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(placeholder),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .width(28.dp)
                            .height(28.dp)
                            .clip(CircleShape)
                    )
                }
            }
            AppText(
                text = text,
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Box(
                contentAlignment = Alignment.Center
            ) {
                Box(
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