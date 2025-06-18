package com.example.ppab_responsi1_kelompok09.presentation.finance

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.data.local.TokenDataStore
import com.example.ppab_responsi1_kelompok09.domain.model.Contact
import com.example.ppab_responsi1_kelompok09.domain.model.Product
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.repository.ProductRepository
import com.example.ppab_responsi1_kelompok09.domain.repository.TransactionItemRepository
import com.example.ppab_responsi1_kelompok09.domain.repository.TransactionRepository
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.DateFilter
import com.example.ppab_responsi1_kelompok09.presentation.components.DateFilterOverlay
import com.example.ppab_responsi1_kelompok09.presentation.components.DrawLine
import com.example.ppab_responsi1_kelompok09.presentation.components.HeaderPageOnBack
import com.example.ppab_responsi1_kelompok09.presentation.components.TonalText
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.presentation.components.formatToCurrency
import com.example.ppab_responsi1_kelompok09.presentation.components.getDateRangeValue
import com.example.ppab_responsi1_kelompok09.presentation.components.getPrevPeriodLabel
import com.example.ppab_responsi1_kelompok09.presentation.components.getPreviousDateRange
import com.example.ppab_responsi1_kelompok09.presentation.transaction.component.DateFilterButton
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary100
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary300
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary500
import com.example.ppab_responsi1_kelompok09.ui.theme.Success
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import com.example.ppab_responsi1_kelompok09.domain.model.TransactionItem
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.placeholder
import com.google.accompanist.placeholder.material3.shimmer
import com.google.accompanist.placeholder.shimmer
import java.time.ZoneId

@Composable
fun FinanceReportScreen(navController: NavController, token : String) {
    // State for loading transactions
    var transactionList by remember { mutableStateOf<List<Transaction>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    // State for filter
    var showOverlay by remember { mutableStateOf(false) }
    var selectedFilter by remember { mutableStateOf(DateFilter.TODAY) }
    val (startDate, endDate) = getDateRangeValue(selectedFilter)

    // Fetch transactions on enter
    LaunchedEffect(Unit) {
        isLoading = true
        loadError = null
        try {
            val data = TransactionRepository.getAllTransactions(token)
            transactionList = data
        } catch (e: Exception) {
            loadError = e.localizedMessage ?: "Error loading transactions"
        } finally {
            isLoading = false
        }
    }

    // Token and products fetch
    val context = LocalContext.current
    val tokenDataStore = remember { TokenDataStore.getInstance(context) }
    val token by tokenDataStore.getToken.collectAsState(initial = null)
    var products by remember { mutableStateOf<List<Product>>(emptyList()) }
    LaunchedEffect(token) {
        token?.let {
            try {
                val fetched = ProductRepository().getAllProducts(it)
                products = fetched
            } catch (e: Exception) {
                // handle error if needed
            }
        }
    }

    // Compute filtered lists once loaded
    val filteredTransaction = remember(transactionList, selectedFilter) {
        transactionList.filter {
            val date = when (it) {
                is Transaction.Sell -> it.date
                is Transaction.Purchase -> it.date
                is Transaction.Bill -> it.date
                else -> null
            } ?: return@filter false
            val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            localDate in startDate..endDate
        }
    }
    val (prevStart, prevEnd) = getPreviousDateRange(selectedFilter)
    val prevTransaction = remember(transactionList, selectedFilter) {
        transactionList.filter {
            val date = when (it) {
                is Transaction.Sell -> it.date
                is Transaction.Purchase -> it.date
                is Transaction.Bill -> it.date
                else -> null
            } ?: return@filter false
            val localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
            localDate in prevStart..prevEnd
        }
    }
    val prevCount = prevTransaction.size
    val currCount = filteredTransaction.size
    val percentChange = when {
        prevCount == 0 && currCount == 0 -> 0f
        prevCount == 0 -> 100f
        else -> ((currCount - prevCount) / prevCount.toFloat()) * 100
    }
    val isUp = currCount >= prevCount

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(White)
            .padding(vertical = 20.dp)
    ) {
        when {
            isLoading -> {
                // Show shimmer placeholders similar to KnowledgeCardSectionLoading
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(White),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    // Header placeholder
                    item {
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
                                        highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
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
                                        highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                    )
                            )
                        }
                        Spacer(Modifier.height(16.dp))
                    }
                    // DateFilterButton placeholder
                    item {
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(44.dp)
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .placeholder(
                                    visible = true,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp),
                                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                )
                        )
                    }
                    // Sections placeholders multiple
                    items(5) {
                        // Placeholder for section title
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .height(20.dp)
                                .fillMaxWidth(0.5f)
                                .placeholder(
                                    visible = true,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(4.dp),
                                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                )
                        )
                        Spacer(Modifier.height(8.dp))
                        // Placeholder for chart or list
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 16.dp)
                                .fillMaxWidth()
                                .height(150.dp)
                                .clip(RoundedCornerShape(12.dp))
                                .placeholder(
                                    visible = true,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(12.dp),
                                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                )
                        )
                        Spacer(Modifier.height(24.dp))
                    }
                }
            }
            loadError != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    AppText(text = "Error: $loadError", color = Color.Red, fontSize = 14.sp)
                }
            }
            else -> {
                Column(modifier = Modifier.fillMaxSize()) {
                    HeaderPageOnBack(
                        onClick = { navController.popBackStack() },
                        text = "Laporan Penjualan"
                    )
                    Column(
                        verticalArrangement = Arrangement.spacedBy(24.dp),
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(horizontal = 16.dp)
                    ) {
                        Spacer(Modifier.height(1.dp))
                        DateFilterButton(
                            onClickShowOverlay = { showOverlay = true },
                            selectedFilter = selectedFilter
                        )
                        TransaksiBerhasilSection(
                            navController = navController,
                            transaction = filteredTransaction,
                            percentChange = percentChange,
                            isUp = isUp,
                            prevPeriodLabel = getPrevPeriodLabel(selectedFilter)
                        )
                        DonutChart(
                            transaction = filteredTransaction,
                            prevTransaction = prevTransaction,
                            prevPeriodLabel = getPrevPeriodLabel(selectedFilter)
                        )
                        PelangganTeratasSection(
                            navController = navController,
                            transaction = filteredTransaction
                        )
                        ProdukTeratasSection(
                            navController = navController,
                            transaction = filteredTransaction,
                            products = products
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                    }
                }
                if (showOverlay) {
                    DateFilterOverlay(
                        onDismiss = { showOverlay = false },
                        onSelected = { selected -> selectedFilter = selected }
                    )
                }
            }
        }
    }
}


@Composable
private fun TransaksiBerhasilSection(
    navController: NavController,
    transaction: List<Transaction>,
    percentChange: Float,
    isUp: Boolean,
    prevPeriodLabel: String
) {
    val sellList = transaction.filterIsInstance<Transaction.Sell>()
    val purchaseList = transaction.filterIsInstance<Transaction.Purchase>()
    val billList = transaction.filterIsInstance<Transaction.Bill>()

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow200(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(16.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText("TRANSAKSI BERHASIL", 14.sp, FontWeight.Bold)
            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { navController.navigate("transaction") }
                )
            ) {
                AppText(
                    text = "Lihat Semua",
                    color = Primary
                )
                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(Dark.copy(0.2f)))
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText(
                text = transaction.size.toString(),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Column (
                horizontalAlignment = Alignment.End
            ) {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(
                            if (isUp) R.drawable.ic_pendapatan_naik else R.drawable.ic_pendapatan_turun
                        ),
                        contentDescription = null,
                        tint = if (isUp) Success else Danger,
                        modifier = Modifier.size(16.dp)
                    )
                    AppText(
                        text = "${"%.0f".format(percentChange)}%",
                        color = if (isUp) Success else Danger,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
                AppText(
                    text = prevPeriodLabel,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
        Box(Modifier.fillMaxWidth().height(1.dp).background(Dark.copy(0.2f)))
        Row( Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            AppText("Penjualan")
            AppText(sellList.size.toString())
        }
        Row( Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            AppText("Pembelian")
            AppText(purchaseList.size.toString())
        }
        Row( Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            AppText("Tagihan")
            AppText(billList.size.toString())
        }
    }
}

// Data class untuk chart
data class DonutChartData(val value: Float, val color: Color, val label: String)

@Composable
fun DonutChart(
    transaction: List<Transaction>,
    prevTransaction: List<Transaction>,
    prevPeriodLabel: String
) {
    val currTotal = transaction.filterIsInstance<Transaction.Sell>().sumOf { it.total }
    val prevTotal = prevTransaction.filterIsInstance<Transaction.Sell>().sumOf { it.total }
    val percentChange = when {
        prevTotal.compareTo(java.math.BigDecimal.ZERO) == 0 && currTotal.compareTo(java.math.BigDecimal.ZERO) == 0 -> 0f
        prevTotal.compareTo(java.math.BigDecimal.ZERO) == 0 -> 100f
        else -> ((currTotal - prevTotal).toFloat() / prevTotal.toFloat()) * 100
    }
    val isUp = currTotal >= prevTotal

    val sellList = transaction.filterIsInstance<Transaction.Sell>()

    val tunai = sellList
        .filter { it.paymentMethod == "Tunai" }
        .sumOf { it.total }

    val qris = sellList
        .filter { it.paymentMethod == "QRIS" }
        .sumOf { it.total }

    val kredit = sellList
        .filter { it.paymentMethod == "Kartu Kredit" }
        .sumOf { it.total }

    val lainnya = sellList
        .filter { it.paymentMethod != "Tunai" && it.paymentMethod != "Kartu Kredit" && it.paymentMethod != "QRIS" }
        .sumOf { it.total }

    val data = listOf(
        DonutChartData(tunai.toFloat(), Primary, "Tunai"),
        DonutChartData(kredit.toFloat(), Primary500, "Kredit"),
        DonutChartData(qris.toFloat(), Primary300, "QRIS"),
        DonutChartData(lainnya.toFloat(), Primary100, "Lainnya")
    )

    val total = data.sumOf { it.value.toDouble() }.toFloat()
    var startAngle = -90f

    val grossIncome = sellList.sumOf { it.total }

    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow200(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(16.dp)
    ) {
        Row (
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText("TOTAL PENDAPATAN KOTOR", 14.sp, FontWeight.Bold)
        }
        DrawLine()
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText(
                text = formatToCurrency(grossIncome),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Column (
                horizontalAlignment = Alignment.End
            ) {
                Row (
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(
                            if (isUp) R.drawable.ic_pendapatan_naik else R.drawable.ic_pendapatan_turun
                        ),
                        contentDescription = null,
                        tint = if (isUp) Success else Danger,
                        modifier = Modifier.size(16.dp)
                    )
                    AppText(
                        text = "${"%.0f".format(percentChange)}%",
                        color = if (isUp) Success else Danger,
                        fontWeight = FontWeight.SemiBold,
                        fontSize = 16.sp
                    )
                }
                AppText(
                    text = prevPeriodLabel,
                    fontSize = 10.sp,
                    fontWeight = FontWeight.Light
                )
            }
        }
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .height(152.dp)
                    .aspectRatio(1f),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .size(152.dp) // Ukuran sama dengan Box
                        .align(Alignment.Center)
                ) {
                    val strokeWidth = 40.dp.toPx() // Lebar stroke proporsional
                    val diameter = size.minDimension - strokeWidth
                    val topLeft = Offset(strokeWidth / 2, strokeWidth / 2)
                    val arcSize = Size(diameter, diameter)
                    var angle = -90f
                    data.forEach { item ->
                        val sweepAngle = if (total == 0f) 0f else (item.value / total) * 360f
                        drawArc(
                            color = item.color,
                            startAngle = angle,
                            sweepAngle = sweepAngle,
                            useCenter = false,
                            style = Stroke(width = strokeWidth),
                            size = arcSize,
                            topLeft = topLeft
                        )
                        angle += sweepAngle
                    }
                }
            }
            Column (
                verticalArrangement = Arrangement.SpaceBetween,
                horizontalAlignment = Alignment.Start,
                modifier = Modifier.height(152.dp)
            ) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(Modifier.size(16.dp).clip(RoundedCornerShape(4.dp)).background(Primary))
                    AppText("Tunai")
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(Modifier.size(16.dp).clip(RoundedCornerShape(4.dp)).background(Primary500))
                    AppText("Kredit")
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(Modifier.size(16.dp).clip(RoundedCornerShape(4.dp)).background(Primary300))
                    AppText("QRIS")
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Box(Modifier.size(16.dp).clip(RoundedCornerShape(4.dp)).background(Primary100))
                    AppText("Lainnya")
                }
            }
        }
        DrawLine()
        Row( Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            AppText("Tunai")
            AppText(formatToCurrency(tunai))
        }
        Row( Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            AppText("QRIS")
            AppText(formatToCurrency(qris))
        }
        Row( Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            AppText("Kredit")
            AppText(formatToCurrency(kredit))
        }
        Row( Modifier.fillMaxWidth(), Arrangement.SpaceBetween) {
            AppText("Lainnya")
            AppText(formatToCurrency(lainnya))
        }
    }
}


@Composable
private fun PelangganTeratasSection(
    navController: NavController,
    transaction: List<Transaction>
) {
    // Ambil hanya penjualan
    val sellList = transaction.filterIsInstance<Transaction.Sell>()

    // Ambil pelanggan teratas
    val topCustomers = sellList
        .groupBy { it.customer.id }
        .map { (_, list) ->
            val contact = list.first().customer
            contact to list.size
        }
        .sortedByDescending { it.second }
        .take(5)

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .dropShadow200(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText("PELANGGAN TERATAS", 14.sp, FontWeight.Bold)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { navController.navigate("contact") }
                )
            ) {
                AppText("Lihat Semua", color = Primary)
                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }

        DrawLine()

        if (topCustomers.isEmpty()) {
            AppText(
                text = "Belum ada data pelanggan.",
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = Modifier.padding(vertical = 16.dp)
            )
        } else {
            Column(
                verticalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                topCustomers.forEach { (customer, count) ->
                    Column {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp)
                                .height(40.dp)
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
//                                Image(
//                                    painter = painterResource(customer.image_kontak),
//                                    contentDescription = null,
//                                    modifier = Modifier
//                                        .size(24.dp)
//                                        .clip(CircleShape),
//                                    contentScale = ContentScale.Crop
//                                )
                                AppText(
                                    text = customer.nama_kontak,
                                    fontWeight = FontWeight.Medium
                                )
                            }
                            AppText(
                                text = "$count Penjualan",
                                fontWeight = FontWeight.Light,
                                fontSize = 10.sp
                            )
                        }
                        DrawLine()
                    }
                }
            }
        }
    }
}

@Composable
private fun ProdukTeratasSection(
    navController: NavController,
    transaction: List<Transaction>,
    products: List<Product>
) {
    // State for loading top products
    var topProducts by remember { mutableStateOf<List<Pair<String, Int>>?>(null) }
    var isLoading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf<String?>(null) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(transaction) {
        isLoading = true
        loadError = null
        try {
            // Ambil hanya penjualan
            val sellList = transaction.filterIsInstance<Transaction.Sell>()
            // Ambil semua item dari penjualan secara asinkron
            val allItems = mutableListOf<TransactionItem>()
            for (sell in sellList) {
                try {
                    val items = TransactionItemRepository.getAllTransactionItems(sell.id) // DUMMY DATA DULU
                    allItems += items
                } catch (_: Exception) {
                    // bisa handle per item error
                }
            }
            // Hitung total penjualan tiap produk
            val computed = allItems
                .groupBy { it.productId }
                .map { (productId, items) ->
                    val totalAmount = items.sumOf { it.amount }
                    productId to totalAmount
                }
                .sortedByDescending { it.second }
                .take(5)
            topProducts = computed
        } catch (e: Exception) {
            loadError = e.localizedMessage ?: "Error loading top products"
            topProducts = emptyList()
        } finally {
            isLoading = false
        }
    }

    Column(
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .padding(16.dp)
    ) {
        Row(
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText("PRODUK TERATAS", 14.sp, FontWeight.Bold)
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null,
                    onClick = { navController.navigate("product") }
                )
            ) {
                AppText("Lihat Semua", color = Primary)
                Icon(
                    painter = painterResource(R.drawable.ic_next),
                    contentDescription = null,
                    tint = Primary,
                    modifier = Modifier.size(16.dp)
                )
            }
        }
        DrawLine()

        when {
            isLoading -> {
                // Show shimmer for items
                Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                    repeat(3) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(40.dp)
                                .placeholder(
                                    visible = true,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(8.dp),
                                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                )
                        ) {
                            Row(
                                horizontalArrangement = Arrangement.spacedBy(8.dp),
                                verticalAlignment = Alignment.CenterVertically,
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(24.dp)
                                        .clip(CircleShape)
                                        .placeholder(
                                            visible = true,
                                            color = Color.LightGray,
                                            shape = CircleShape,
                                            highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                        )
                                )
                                Box(
                                    modifier = Modifier
                                        .width(100.dp)
                                        .height(14.dp)
                                        .placeholder(
                                            visible = true,
                                            color = Color.LightGray,
                                            shape = RoundedCornerShape(4.dp),
                                            highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                        )
                                )
                            }
                            Box(
                                modifier = Modifier
                                    .width(60.dp)
                                    .height(14.dp)
                                    .placeholder(
                                        visible = true,
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(4.dp),
                                        highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                    )
                            )
                        }
                        DrawLine()
                    }
                }
            }
            loadError != null -> {
                AppText(text = "Error: $loadError", color = Color.Red, fontSize = 12.sp)
            }
            else -> {
                val itemsList = topProducts.orEmpty()
                if (itemsList.isEmpty()) {
                    AppText(
                        text = "Belum ada produk terjual.",
                        fontSize = 12.sp,
                        color = Color.Gray,
                        modifier = Modifier.padding(vertical = 16.dp)
                    )
                } else {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(4.dp),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        itemsList.forEach { (productId, totalSold) ->
                            val product = products.find { it.id == productId }
                            if (product != null) {
                                Column {
                                    Row(
                                        horizontalArrangement = Arrangement.SpaceBetween,
                                        verticalAlignment = Alignment.CenterVertically,
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(vertical = 8.dp)
                                            .height(40.dp)
                                    ) {
                                        Row(
                                            horizontalArrangement = Arrangement.spacedBy(8.dp),
                                            verticalAlignment = Alignment.CenterVertically,
                                        ) {
                                            AsyncImage(
                                                model = product.productImage,
                                                contentDescription = null,
                                                modifier = Modifier
                                                    .size(24.dp)
                                                    .clip(CircleShape),
                                                contentScale = ContentScale.Crop
                                            )
                                            AppText(
                                                text = product.productName,
                                                fontWeight = FontWeight.Medium
                                            )
                                        }
                                        AppText(
                                            text = "$totalSold Terjual",
                                            fontWeight = FontWeight.Light,
                                            fontSize = 10.sp
                                        )
                                    }
                                    DrawLine()
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

