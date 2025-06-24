package com.example.ppab_responsi1_kelompok09.presentation.more

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.dp
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.User
import com.example.ppab_responsi1_kelompok09.presentation.components.HeaderGradient
import com.example.ppab_responsi1_kelompok09.presentation.components.ProfileContainer
import com.example.ppab_responsi1_kelompok09.presentation.components.TonalIcon
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import com.example.ppab_responsi1_kelompok09.presentation.login.AuthViewModel

@Composable
fun MoreScreen (
    navController: NavController, loginNavController: NavController, authViewModel: AuthViewModel
) {

    val user by authViewModel.user.collectAsState()

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
                imageUrl = user?.profilePhoto, // Use the profile photo URL from API
                placeholder = R.drawable.img_profile_picture, // Fallback image
                text = user?.name ?: "",
                isLogin = user != null,
                onClick = { navController.navigate("profile/${user?.id ?: ""}") }
            )
            Box (
                modifier = Modifier
                    .padding(top = 4.dp)
                    .dropShadow200(8.dp)
                    .clip(RoundedCornerShape(8.dp))
            ) {
                OptionItemDisabled(
                    isAkun = true,
                    icon = R.drawable.ic_login_fill,
                    user = user
                )
            }
            AppText(
                text = "Manajemen",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            ManajemenItemContainer(user, navController)
            AppText(
                text = "Laporan Keuangan",
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp
            )
            LaporanKeuanganItemContainer(navController)
            Box (
                modifier = Modifier
                    .dropShadow200(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .fillMaxWidth()
                    .height(60.dp)
            ) {
                Logout(loginNavController, authViewModel)
            }
        }
    }
}

@Composable
private fun ManajemenItemContainer (
    user: User?, navController: NavController
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow200(8.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        OptionItem(
            onClick = { navController.navigate("profile/${user?.id?: ""}") },
            text = "Profil Bisnis",
            icon = R.drawable.ic_profil_bisnis)
        Spacer(modifier = Modifier.height(0.5.dp).background(Gray))
        OptionItem(
            onClick = { navController.navigate("balance") },
            text = "Kelola Saldo",
            icon = R.drawable.ic_saldo_fill)
    }
}

@Composable
private fun LaporanKeuanganItemContainer(
    navController: NavController
) {
    Column (
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow200(8.dp)
            .clip(RoundedCornerShape(8.dp))
    ) {
        OptionItem(
            onClick = { navController.navigate("laporan_penjualan") },
            text = "Laporan Penjualan",
            icon = R.drawable.ic_penjualan_fill)
        Spacer(modifier = Modifier.height(0.5.dp).background(Gray))
        OptionItem(
            onClick = { navController.navigate("laporan_pembelian") },
            text = "Laporan Pembelian",
            icon = R.drawable.ic_pembelian_fill)
        Spacer(modifier = Modifier.height(0.5.dp).background(Gray))
        OptionItem(
            onClick = { navController.navigate("laporan_tagihan") },
            text = "Laporan Tagihan",
            icon = R.drawable.ic_tagihan_fill)
    }
}

@Composable
private fun OptionItemDisabled (
    onClick : () -> Unit = {}, isAkun : Boolean = false, user: User?, text : String = "", icon : Int
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(White)
            .padding(horizontal = 16.dp)
            .height(60.dp),
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
                    text = user?.namaUsaha ?:"",
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
    }
}

@Composable
private fun OptionItem (
    onClick : () -> Unit = {}, isAkun : Boolean = false, text : String = "", icon : Int
) {
    Row (
        modifier = Modifier
            .clickable{ onClick() }
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
            painter = painterResource(R.drawable.ic_next),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
            tint = Primary
        )
    }
}

@Composable
private fun Logout (loginNavController: NavController, authViewModel: AuthViewModel) {
    Row (
        modifier = Modifier
            .clickable{
                authViewModel.signOut()
                loginNavController.navigate("login")
            }
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
                iconRes = R.drawable.ic_logout,
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
            painter = painterResource(R.drawable.ic_next),
            contentDescription = null,
            modifier = Modifier
                .size(24.dp),
            tint = Danger
        )
    }
}