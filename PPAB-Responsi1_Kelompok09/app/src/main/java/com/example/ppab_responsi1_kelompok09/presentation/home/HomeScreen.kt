package com.example.ppab_responsi1_kelompok09.presentation.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.presentation.components.ProfileContainer
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary900
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.presentation.components.KnowledgeCard
import com.example.ppab_responsi1_kelompok09.presentation.components.TonalIcon
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.HomeTextHeader
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.domain.model.KnowledgeCardItem
import com.example.ppab_responsi1_kelompok09.domain.model.MenuItem
import com.example.ppab_responsi1_kelompok09.domain.model.News
import com.example.ppab_responsi1_kelompok09.domain.model.TabelItem
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.repository.NewsRepository
import com.example.ppab_responsi1_kelompok09.domain.model.User
import com.example.ppab_responsi1_kelompok09.domain.repository.TransactionRepository
import com.example.ppab_responsi1_kelompok09.presentation.components.DateFilter
import com.example.ppab_responsi1_kelompok09.presentation.components.formatToCurrency
import com.example.ppab_responsi1_kelompok09.presentation.components.getDateRangeValue
import com.example.ppab_responsi1_kelompok09.presentation.components.getPrevPeriodLabel
import com.example.ppab_responsi1_kelompok09.presentation.components.getPreviousDateRange
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Success
import com.example.ppab_responsi1_kelompok09.presentation.login.AuthViewModel
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.shimmer
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.ZoneId
import kotlin.compareTo
import kotlin.math.abs
import kotlin.text.compareTo
import kotlin.text.toDouble
import kotlin.text.toFloat

@Composable
fun HomeScreen(navController: NavController, authViewModel: AuthViewModel, user: User?) {

    val transaction = TransactionRepository.getAllTransaction()

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .verticalScroll(rememberScrollState())
    ) {
        Box (
            Modifier
                .height(1280.dp)
        ) {
            HeaderHome(authViewModel = authViewModel, navController, user)
            Column (
                modifier = Modifier.offset(y = 206.dp),
                verticalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                PendapatanCard()
                MenuGrid(navController)
                KnowledgeCardSection(navController)
                PesananTerbaru(navController)
            }
            Box(modifier = Modifier
                .offset(y = 484.dp)
                .fillMaxWidth()
                .background(Gray.copy(0.1f))
                .height(300.dp)
            )
        }
    }
}

@Composable
private fun HeaderHome(authViewModel: AuthViewModel, navController: NavController, user: User?) {

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
                imageUrl = user?.profilePhoto, // Use the profile photo URL from API
                placeholder = R.drawable.img_profile_picture, // Fallback image
                text = user?.name ?: "",
                isLogin = user != null,
                onClick = {
                    navController.navigate("profile")
                }
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
    val transaction = TransactionRepository.getAllTransaction()
    val selectedFilter = DateFilter.TODAY
    val (startDate, endDate) = getDateRangeValue(selectedFilter)
    val (prevStart, prevEnd) = getPreviousDateRange(selectedFilter)

    val todaySales = transaction.filterIsInstance<Transaction.Sell>().filter {
        val localDate = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        localDate in startDate..endDate
    }
    val prevSales = transaction.filterIsInstance<Transaction.Sell>().filter {
        val localDate = it.date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
        localDate in prevStart..prevEnd
    }

    val todayTotal = todaySales.sumOf { it.total }
    val prevTotal = prevSales.sumOf { it.total }

    val percentChange = when {
        prevTotal.compareTo(java.math.BigDecimal.ZERO) == 0 && todayTotal.compareTo(java.math.BigDecimal.ZERO) == 0 -> 0f
        prevTotal.compareTo(java.math.BigDecimal.ZERO) == 0 -> 100f
        else -> ((todayTotal - prevTotal).toFloat() / prevTotal.toFloat()) * 100
    }
    val isUp = todayTotal >= prevTotal

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
                    iconRes = R.drawable.ic_pendapatan,
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
                        text = formatToCurrency(todayTotal),
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
                        painter = if (isUp) painterResource(R.drawable.ic_pendapatan_naik) else painterResource(R.drawable.ic_pendapatan_turun),
                        contentDescription = null,
                        tint = if (isUp) Success else Danger,
                        modifier = Modifier.width(16.dp)
                    )
                    AppText (
                        text = "%.0f".format(abs(percentChange)) + "%",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = if (isUp) Success else Danger
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
private fun MenuGrid(
    navController: NavController
) {
    val gridItems = listOf(
        MenuItem("Transaksi", R.drawable.ic_transaksi_fill) { navController.navigate("transaction") },
        MenuItem("Saldo", R.drawable.ic_saldo_fill) { navController.navigate("balance") },
        MenuItem("Produk", R.drawable.ic_produk_fill) { navController.navigate("product") },
        MenuItem("Pelanggan", R.drawable.ic_pelanggan_fill) { navController.navigate("contact") },
        MenuItem("Keuangan", R.drawable.ic_keuangan_fill) { navController.navigate("finance_report") },
        MenuItem("Penjualan", R.drawable.ic_penjualan_fill) { navController.navigate("transaction?category=Penjualan") },
        MenuItem("Pembelian", R.drawable.ic_pembelian_fill) { navController.navigate("transaction?category=Pembelian") },
        MenuItem("Tagihan", R.drawable.ic_tagihan_fill) { navController.navigate("transaction?category=Tagihan") }
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
                    isClickable = true,
                    onClick = item.onClick,
                    iconHeight = 28.dp,
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
private fun KnowledgeCardSection(navController: NavController) {
    val knowledgeCardItem = remember { mutableStateListOf<KnowledgeCardItem>() }
    var newsList by remember { mutableStateOf<List<News>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        try {
            newsList = NewsRepository.getAll(5)
        } catch(e: Exception) {
            errorMsg = "Gagal memuat berita"
        } finally {
            newsList.forEach(){ news ->
                knowledgeCardItem.add(KnowledgeCardItem(news.id, news.imageUrl, news.title, news.description))
            }
            isLoading = false
        }
    }
    if (isLoading) {
        KnowledgeCardSectionLoading()

    } else if (errorMsg != null) {

        AppText(errorMsg!!, color = Danger)

    } else {
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
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) { navController.navigate("news") }
            ) {
                AppText(
                    text = "Lihat semua",
                    color = Primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                )
                Icon (
                    painter = painterResource(R.drawable.ic_next),
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
                    imageUrl = item.imageUrl,
                    title = item.title,
                    description = item.description,
                    onClick = { navController.navigate("news_detail/" + item.id ) }
                )
            }
        }
    }
}

@Composable
private fun PesananTerbaru(
    navController: NavController
) {
    val transaction = TransactionRepository.getAllTransaction()
        .filterIsInstance<Transaction.Sell>()
        .sortedByDescending { it.date }
        .take(5)

    Column (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .padding(top = 16.dp)
            .dropShadow200(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(vertical = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Row (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            HomeTextHeader("PESANAN TERBARU")
            Row (
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() },
                        onClick = { navController.navigate("transaction") }
                    )
            ) {
                AppText(
                    text = "Lihat semua",
                    color = Primary,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                )
                Icon (
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.height(20.dp)
                )
            }
        }
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .background(Gray.copy(0.5f))
                .height(1.dp)
        )
        LazyColumn {
            items(transaction.size) { trx ->
                val item = transaction[trx]
                TabelItemRow(
//                    image = item.customer.image_kontak,
                    name = item.customer.nama_kontak,
                    date = SimpleDateFormat("dd MMM yyyy").format(item.date),
                    money = "+ " + formatToCurrency(item.total),
                    onClick = {
                        navController.navigate("penjualan_detail/${item.id}")
                    }
                )
            }
        }
    }
}

@Composable
private fun TabelItemRow(
//    image : Int,
    name : String,
    date : String,
    money : String,
    onClick : () -> Unit = {}
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .clickable{ onClick }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
//            Image (
//                painter = painterResource(image),
//                contentDescription = null,
//                contentScale = ContentScale.Crop,
//                modifier = Modifier
//                    .size(24.dp)
//                    .clip(CircleShape)
//            )
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

@Composable
private fun KnowledgeCardSectionLoading() {
    Spacer(Modifier.height(10.dp))
    Row(
        modifier = Modifier
            .padding(horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        Box(
            modifier = Modifier
                .size(width = 80.dp, height = 20.dp)
                .placeholder(
                    visible = true,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color(0xFFBBBBBB)
                    )
                )
        )
        Box(
            modifier = Modifier
                .weight(1f)
                .height(1.dp)
                .background(Color.Gray.copy(0.5f))
        )
        Box(
            modifier = Modifier
                .width(70.dp)
                .height(20.dp)
                .placeholder(
                    visible = true,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(4.dp),
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color(0xFFBBBBBB)
                    )
                )
        )
    }
    Spacer(Modifier.height(16.dp))
    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(3) {
            Box(
                modifier = Modifier
                    .width(240.dp)
                    .height(150.dp)
                    .placeholder(
                        visible = true,
                        color = Color.LightGray,
                        shape = RoundedCornerShape(12.dp),
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color(0xFFBBBBBB)
                        )
                    )
            )
        }
    }
}