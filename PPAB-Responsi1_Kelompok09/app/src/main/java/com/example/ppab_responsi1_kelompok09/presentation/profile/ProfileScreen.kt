package com.example.ppab_responsi1_kelompok09.presentation.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.User
//import com.example.ppab_responsi1_kelompok09.domain.repository.UserRepository
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.TopSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.PrimaryGradient
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun ProfileScreen (user: User?, navController: NavController) {
    when {
        user == null -> {
            // Show a loading or error state instead of a blank box
            Box(
                Modifier
                    .fillMaxSize()
                    .background(Dark),
                contentAlignment = Alignment.Center
            ) {
                AppText(text = "Memuat profil...", color = White, fontSize = 16.sp)
            }
        }
        else -> {
            Box(
                modifier = Modifier.fillMaxSize()
            ) {
                Column (
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    ImageBox(user, navController)
                    InformasiUsahaSection(user)
                    AlamatUsahaSection(user)
                }
            }
        }
    }
}

@Composable
private fun ImageBox(user: User?, navController: NavController) {
    Box (
        modifier = Modifier.fillMaxWidth()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(256.dp)
                .background(PrimaryGradient),
        ) {
            Column (
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .align(Alignment.BottomCenter)
            ) {
                Spacer(modifier = Modifier.height(32.dp))
                // Profile image with safe fallback
                if (!user?.profilePhoto.isNullOrBlank()) {
                    AsyncImage(
                        model = user?.profilePhoto,
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .border(2.dp, White, CircleShape)
                    )
                } else {
                    Image(
                        painter = painterResource(R.drawable.img_profile_picture),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .size(96.dp)
                            .clip(CircleShape)
                            .border(2.dp, White, CircleShape)
                    )
                }
                Spacer(modifier = Modifier.height(12.dp))
                AppText(
                    text = user?.name ?: "-",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = White
                )
                AppText(
                    text = user?.email ?: "-",
                    fontSize = 14.sp,
                    color = White
                )
            }
        }
        Column (
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            TopSpacer()
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .dropShadow200(999.dp)
                        .background(White)
                        .clickable{ navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter =  painterResource(R.drawable.ic_previous),
                        contentDescription = null,
                        tint = Dark,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .dropShadow200(999.dp)
                        .background(White)
                        .clickable{ navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter =  painterResource(R.drawable.ic_titik_tiga),
                        contentDescription = null,
                        tint = Dark,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun InformasiUsahaSection(user: User?) {
    data class InfoUsaha (
        val text: String,
        val userData: String
    )

    val InformasiUsahaList = listOf(
        InfoUsaha("Nama Usaha", user?.namaUsaha ?: "-"),
        InfoUsaha("Nomor Telepon", user?.nomorHandphone ?: "-"),
        InfoUsaha("Tipe Usaha", user?.tipeUsaha ?: "-"),
        InfoUsaha("NPWP Pribadi", user?.npwp ?: "-")
    )

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        AppText(
            text = "Informasi Usaha",
            fontWeight = FontWeight.SemiBold
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(InformasiUsahaList.size) { index ->
                val item = InformasiUsahaList[index]
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AppText(
                        text = item.text,
                        color = Dark.copy(0.5f)
                    )
                    AppText(
                        text = item.userData
                    )
                }
            }
        }
    }
}

@Composable
private fun AlamatUsahaSection(user: User?) {
    data class AlamatUsaha (
        val text: String,
        val userData: String
    )

    val AlamatUsahaList = listOf(
        AlamatUsaha("Provinsi", user?.provinsi ?: "-"),
        AlamatUsaha("Kabupaten/Kota", user?.kabupatenKota ?: "-"),
        AlamatUsaha("Kecamatan", user?.kecamatan ?: "-"),
        AlamatUsaha("Kelurahan", user?.desa ?: "-")
    )

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        AppText(
            text = "Alamat Usaha",
            fontWeight = FontWeight.SemiBold
        )
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
        ) {
            items(AlamatUsahaList.size) { index ->
                val item = AlamatUsahaList[index]
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    AppText(
                        text = item.text,
                        color = Dark.copy(0.5f)
                    )
                    AppText(
                        text = item.userData
                    )
                }
            }
        }
    }
}