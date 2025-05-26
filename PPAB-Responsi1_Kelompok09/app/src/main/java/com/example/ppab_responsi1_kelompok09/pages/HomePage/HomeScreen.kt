package com.example.ppab_responsi1_kelompok09.pages.HomePage

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.rememberScrollableState
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.layout.approachLayout
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.common.component.ProfileContainer
import com.example.ppab_responsi1_kelompok09.ui.theme.Poppins
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary900
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.common.component.BottomSpacer
import com.example.ppab_responsi1_kelompok09.common.component.KnowledgeCard
import com.example.ppab_responsi1_kelompok09.common.component.TonalIcon
import com.example.ppab_responsi1_kelompok09.common.style.AppText
import com.example.ppab_responsi1_kelompok09.common.style.HomeTextHeader
import com.example.ppab_responsi1_kelompok09.common.style.dropShadow200
import com.example.ppab_responsi1_kelompok09.data.KnowledgeCardItem
import com.example.ppab_responsi1_kelompok09.data.MenuItem
import com.example.ppab_responsi1_kelompok09.data.TabelItem
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Success

@Composable
fun HomeScreen(navController: NavController) {
    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
    ) {
        Box (
            Modifier
                .height(1336.dp)
        ) {
            HeaderHome()
            Column (
                modifier = Modifier.offset(y = 206.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                PendapatanCard()
                MenuGrid()
                UpgradeButton(navController)
                KnowledgeCardSection()
                PesananTerbaru()
            }
            Box(modifier = Modifier
                .offset(y = 548.dp)
                .fillMaxWidth()
                .background(Gray.copy(0.1f))
                .height(300.dp)
            )
        }
    }
}

@Composable
private fun HeaderHome() {
    Box (
        modifier = Modifier
            .fillMaxWidth()
            .height(236.dp)
            .background(
                brush = Brush.linearGradient(
                    colors = listOf(Primary900, Primary),
                    start = Offset(0f, Float.POSITIVE_INFINITY),
                    end = Offset(Float.POSITIVE_INFINITY, 0f)
                ))
            .dropShadow200(0.dp)
    ) {
        Box (
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .offset(y = 52.dp)
        )
        {
            ProfileContainer(
                icon = R.drawable.img_profile_picture,
                text = "Biru",
                isLogin = true
            )
        }
        Column (
            modifier = Modifier
                .offset(x = 16.dp, y = 118.dp)
        ) {
            AppText(
                text = "Selamat Datang!",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = White
            )
            AppText(
                text = "Kelola semua usahamu!",
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal,
                color = White
            )
        }
    }
}

@Composable
private fun PendapatanCard() {
    Row (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(60.dp)
            .dropShadow200(8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth()
        ) {
            Row (
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                TonalIcon(
                    iconRes = R.drawable.pendapatan,
                    iconHeight = 24.dp,
                    iconBackground = Dark,
                    boxSize = 40.dp
                )
                Column {
                    AppText(
                        text = "Pendapatan Hari Ini",
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Normal
                    )
                    AppText(
                        text = "Rp1.000.000",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
            Column {
                Row (
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.pendapatan_naik),
                        contentDescription = null,
                        tint = Success,
                        modifier = Modifier.width(16.dp)
                    )
                    AppText (
                        text = "50%",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Success
                    )
                }
                AppText (
                    text = "Dari kemarin",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
    }
}

@Composable
private fun MenuGrid() {
    val gridItems = listOf(
        MenuItem("Transaksi", R.drawable.transaction_fill),
        MenuItem("Saldo", R.drawable.saldo),
        MenuItem("Produk", R.drawable.produk_fill),
        MenuItem("Pelanggan", R.drawable.pelanggan_fill),
        MenuItem("Keuangan", R.drawable.keuangan),
//      Harusnya fill
        MenuItem("Penjualan", R.drawable.penjualan),
        MenuItem("Pembelian", R.drawable.pembelian),
        MenuItem("Tagihan", R.drawable.tagihan)
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(166.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        items(gridItems.size) { i ->
            val item = gridItems[i]
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                TonalIcon(
                    iconHeight = 24.dp,
                    iconRes = item.icon,
                    boxSize = 48.dp
                )
                Spacer(modifier = Modifier.height(8.dp))
                AppText(
                    text = item.label,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Medium
                )
            }
        }
    }
}

@Composable
private fun UpgradeButton(navController: NavController) {
    Row (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .fillMaxWidth()
            .height(40.dp)
            .clip(RoundedCornerShape(16.dp))
            .clickable{}
            .background(Success.copy(0.1f)),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row (
            modifier = Modifier.padding(start = 12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Icon(
                painter = painterResource(R.drawable.premium),
                contentDescription = null,
                tint = Success
            )
            AppText(
                text = "Peroleh semua fitur!",
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Success
            )
        }
        Icon(
            painter = painterResource(R.drawable.next),
            contentDescription = null,
            tint = Success,
            modifier = Modifier.padding(end = 12.dp)
        )
    }
}

@Composable
private fun KnowledgeCardSection() {
    val knowledgeCardItem = listOf(
        KnowledgeCardItem(R.drawable.img_business_1, "Mau Jadi Pebisnis Sukses? Yuk Terapkan 7 Kebiasaan Positif ...", "Ada begitu banyak contoh pebisnis sukses diberbagai ..."),
        KnowledgeCardItem(R.drawable.img_business_2, "Mau Jadi Pebisnis Sukses? Yuk Terapkan 7 Kebiasaan Positif ...", "Ada begitu banyak contoh pebisnis sukses diberbagai ..."),
        KnowledgeCardItem(R.drawable.img_business_3, "Mau Jadi Pebisnis Sukses? Yuk Terapkan 7 Kebiasaan Positif ...", "Ada begitu banyak contoh pebisnis sukses diberbagai ..."),
        KnowledgeCardItem(R.drawable.img_business_4, "Mau Jadi Pebisnis Sukses? Yuk Terapkan 7 Kebiasaan Positif ...", "Ada begitu banyak contoh pebisnis sukses diberbagai ..."),
        )

    Spacer(Modifier.height(10.dp))
    Row (
        modifier = Modifier
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        HomeTextHeader(text = "BERITA")
        Box (
            modifier = Modifier
                .weight(1f)
                .background(Gray.copy(0.5f))
                .height(1.dp)
        )
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AppText(
                text = "Lihat semua",
                color = Primary,
                fontSize = 12.sp,
                fontWeight = FontWeight.Normal
            )
            Icon (
                painter = painterResource(R.drawable.next),
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.height(20.dp)
            )
        }
    }
    LazyRow (
        modifier = Modifier
            .fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items (knowledgeCardItem.size) { i ->
            val item = knowledgeCardItem[i]
            KnowledgeCard(
                imageRes = item.imageRes,
                title = item.title,
                description = item.description
            )
        }
    }
}

@Composable
private fun PesananTerbaru() {
    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .dropShadow200(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row (
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HomeTextHeader("PESANAN TERBARU")
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                AppText(
                    text = "Lihat semua",
                    color = Primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal
                )
                Icon (
                    painter = painterResource(R.drawable.next),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.height(20.dp)
                )
            }
        }
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .background(Gray.copy(0.5f))
                .height(1.dp)
        )
        TabelPesanan()
    }
}

@Composable
private fun TabelPesanan() {
    val tabelItem = listOf(
        TabelItem(R.drawable.img_profile_picture, "John Doe", "11 Mei 2025", "+ Rp100.000"),
        TabelItem(R.drawable.img_profile_picture, "Ariel Josua", "09 Mei 2025", "+ Rp200.000"),
        TabelItem(R.drawable.img_profile_picture, "Aufa Fawwaz", "09 Mei 2025", "+ Rp1.000.000"),
        TabelItem(R.drawable.img_profile_picture, "Aril Fadla Huda...", "01 Mei 2025", "+ Rp300.000"),
        TabelItem(R.drawable.img_profile_picture, "Budiman", "01 Januari 2025", "+ Rp300.000"),
    )

    LazyColumn (
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(tabelItem.size) { i ->
            val item = tabelItem[i]
            TabelItemRow(
                image = item.imageRes,
                name = item.name,
                date = item.date,
                money = item.money
            )
        }
    }
}

@Composable
private fun TabelItemRow(
    image : Int,
    name : String,
    date : String,
    money : String
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image (
                painter = painterResource(image),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(24.dp)
                    .clip(CircleShape)
            )
            Column {
                AppText(
                    text = name,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp
                )
                AppText(
                    text = date,
                    fontWeight = FontWeight.ExtraLight,
                    fontSize = 10.sp
                )
            }
        }
        AppText(
            text = money,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp,
            color = Success
        )
    }
}