package com.example.ppab_responsi1_kelompok09.pages.MorePage

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.common.component.HeaderGradient
import com.example.ppab_responsi1_kelompok09.common.component.ProfileContainer
import com.example.ppab_responsi1_kelompok09.common.component.TonalIcon
import com.example.ppab_responsi1_kelompok09.common.style.AppText
import com.example.ppab_responsi1_kelompok09.common.style.dropShadow200
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun MoreScreen (navController: NavController) {
    Box (
        modifier = Modifier
            .fillMaxSize()
            .background(White)
    ) {
        HeaderGradient()
        Column (
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
                .offset(y = 52.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            ProfileContainer(
                R.drawable.img_profile_picture,
                "Biru",
                true
            )
            Box (
                modifier = Modifier
                    .padding(top = 4.dp)
                    .dropShadow200(8.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                ManajemenItem(
                    true,
                    "",
                    R.drawable.pelanggan_fill
                )
            }
            AppText(
                text = "Manajemen",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            ManajemenItemContainer()
            Box (
                modifier = Modifier
                    .dropShadow200(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Logout(navController)
            }
        }
    }
}

@Composable
private fun ManajemenItemContainer () {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow200(8.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        ManajemenItem(false, "Profil Bisnis", R.drawable.home_fill)
        Spacer(modifier = Modifier.height(0.5.dp).background(Gray))
        ManajemenItem(false, "Kelola Saldo", R.drawable.home_fill)
        Spacer(modifier = Modifier.height(0.5.dp).background(Gray))
        ManajemenItem(false, "Lihat Laporan Keuangan", R.drawable.home_fill)
    }
}

@Composable
private fun ManajemenItem (
    isAkun : Boolean = false,
    text : String,
    icon : Int
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 16.dp)
            .height(60.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TonalIcon(
                iconHeight = 24.dp,
                iconRes = icon,
                boxSize = 40.dp
            )
            if (isAkun == true) {
                AppText(
                    text = "Akun saya",
                    color = Primary,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
            } else {
                AppText(
                    text = text,
                    fontWeight = FontWeight.Normal,
                    fontSize = 14.sp
                )
            }
        }
        Icon(
            painter = painterResource(R.drawable.next),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
            tint = Primary
        )
    }
}

@Composable
private fun Logout (navController: NavController) {
    Row (
        modifier = Modifier
            .clickable{ navController.navigate("login") }
            .fillMaxSize()
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TonalIcon(
                iconHeight = 24.dp,
                iconRes = R.drawable.home_fill,
                iconBackground = Danger,
                boxSize = 40.dp
            )
            AppText(
                text = "Logout",
                color = Danger,
                fontWeight = FontWeight.Normal,
                fontSize = 14.sp
            )
        }
        Icon(
            painter = painterResource(R.drawable.next),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
            tint = Danger
        )
    }
}