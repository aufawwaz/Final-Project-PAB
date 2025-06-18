package com.example.ppab_responsi1_kelompok09.presentation.transaction

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.presentation.components.BottomSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomButton
import com.example.ppab_responsi1_kelompok09.presentation.components.PageHeader
import com.example.ppab_responsi1_kelompok09.presentation.components.SearchBarFilter
import com.example.ppab_responsi1_kelompok09.presentation.components.TonalIcon
import com.example.ppab_responsi1_kelompok09.presentation.components.TransactionCard
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.repository.TransactionRepository
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.shimmer
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun TransactionScreen(navController: NavController = rememberNavController(), initialCategory: String = "Semua", token: String) {
    // State for loading and data
    var transactionList by remember { mutableStateOf<List<Transaction>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var loadError by remember { mutableStateOf<String?>(null) }

    // Fetch data when entering screen
    LaunchedEffect(Unit) {
        isLoading = true
        loadError = null
        try {
            val data = TransactionRepository.getAllTransactions(token)
            transactionList = data
        } catch (e: Exception) {
            loadError = e.localizedMessage ?: "Error loading data"
        } finally {
            isLoading = false
        }
    }

    // Selected category and search
    var selectedCategory by remember { mutableStateOf(initialCategory) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    // Filtered list based on loaded data
    val filteredList = remember(transactionList, selectedCategory, searchQuery) {
        transactionList.let { list ->
            val byCategory = when (selectedCategory) {
                "Penjualan" -> list.filterIsInstance<Transaction.Sell>()
                "Pembelian" -> list.filterIsInstance<Transaction.Purchase>()
                "Tagihan" -> list.filterIsInstance<Transaction.Bill>()
                else -> list
            }
            byCategory.filter {
                when (it) {
                    is Transaction.Sell -> it.customer.nama_kontak.contains(searchQuery, ignoreCase = true)
                    is Transaction.Purchase -> it.supplier.nama_kontak.contains(searchQuery, ignoreCase = true)
                    is Transaction.Bill -> it.customer.nama_kontak.contains(searchQuery, ignoreCase = true)
                    else -> false
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        if (isLoading) {
            // Show shimmer loading
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                // Show header placeholders
                item {
                    Spacer(Modifier.height(16.dp))
                    // PageHeader placeholder
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .height(24.dp)
                            .fillMaxWidth(0.5f)
                            .placeholder(
                                visible = true,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                            )
                    )
                }
                // Category placeholders
                item {
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(3) {
                            Box(
                                modifier = Modifier
                                    .height(44.dp)
                                    .width(100.dp)
                                    .clip(RoundedCornerShape(16.dp))
                                    .placeholder(
                                        visible = true,
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(16.dp),
                                        highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                    )
                            )
                        }
                    }
                }
                // Search bar placeholder
                item {
                    Box(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .height(44.dp)
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.LightGray.copy(alpha = 0.3f))
                            .placeholder(
                                visible = true,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(8.dp),
                                highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                            )
                    )
                }
                // Item placeholders
                items(5) {
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                            .height(80.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .padding(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth(0.3f)
                                .height(12.dp)
                                .placeholder(
                                    visible = true,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(4.dp),
                                    highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                )
                        )
                        Spacer(Modifier.height(8.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(44.dp)
                                    .clip(RoundedCornerShape(8.dp))
                                    .placeholder(
                                        visible = true,
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(8.dp),
                                        highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                    )
                            )
                            Box(
                                modifier = Modifier
                                    .weight(1f)
                                    .height(20.dp)
                                    .placeholder(
                                        visible = true,
                                        color = Color.LightGray,
                                        shape = RoundedCornerShape(4.dp),
                                        highlight = PlaceholderHighlight.shimmer(highlightColor = Color(0xFFBBBBBB))
                                    )
                            )
                        }
                    }
                }
            }
        } else if (loadError != null) {
            // Show error state
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                AppText(text = "Error: $loadError", color = Color.Red, fontSize = 14.sp)
            }
        } else {
            // Show actual content when data loaded
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item {
                    PageHeader(
                        pagetitle = "Transaksi",
                        title = "Total Transaksi",
                        iconRes = R.drawable.ic_transaksi_fill,
                        description = transactionList.size.toString() + " Transaksi"
                    )
                }
                item {
                    KategoriTransaksi(
                        selectedCategory = selectedCategory,
                        onCategorySelected = { selectedCategory = it }
                    )
                }
                item {
                    AppText(
                        text = "Transaksi Hari ini",
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                item {
                    SearchBarFilter(Modifier, "Cari Transaksi", onSearch = { searchQuery = it })
                }
                items(filteredList) { transactionItem ->
                    val iconRes = when (transactionItem) {
                        is Transaction.Sell -> R.drawable.ic_penjualan_fill
                        is Transaction.Purchase -> R.drawable.ic_pembelian_fill
                        is Transaction.Bill -> R.drawable.ic_tagihan_fill
                        else -> R.drawable.ic_transaksi_fill
                    }
                    val currentId = when (transactionItem) {
                        is Transaction.Sell -> transactionItem.id
                        is Transaction.Purchase -> transactionItem.id
                        is Transaction.Bill -> transactionItem.id
                        else -> "-"
                    }
                    val detailRoute = when (transactionItem) {
                        is Transaction.Sell -> "penjualan_detail/$currentId"
                        is Transaction.Purchase -> "pembelian_detail/$currentId"
                        is Transaction.Bill -> "tagihan_detail/$currentId"
                        else -> ""
                    }
                    Column(
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color.White)
                            .clickable { if(detailRoute.isNotEmpty()) navController.navigate(detailRoute) }
                            .padding(8.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        AppText(
                            text = currentId,
                            fontWeight = FontWeight.Normal,
                            fontSize = 12.sp
                        )
                        Spacer(
                            modifier = Modifier
                                .fillMaxWidth()
                                .background(Color.Gray.copy(0.3f))
                                .height(0.5.dp)
                        )
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(12.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            TonalIcon(
                                iconRes = iconRes,
                                iconHeight = 24.dp,
                                boxSize = 44.dp
                            )
                            TransactionCard(
                                transaction = transactionItem,
                                isIdInCard = false
                            )
                        }
                    }
                }
                item { BottomSpacer() }
            }
            Box(
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .offset(x = -(12.dp), y = -(138.dp))
                    .width(200.dp)
            ) {
                // Placeholder for Floating Action Button or similar
            }
        }
    }
}

@Composable
private fun KategoriTransaksi (
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        KategoriTransaksiItem(
            iconRes = R.drawable.ic_penjualan_fill,
            category = "Penjualan",
            isSelected = selectedCategory == "Penjualan",
            onClick = {
                if (selectedCategory == "Penjualan") onCategorySelected("Semua")
                else onCategorySelected("Penjualan")
            },
            modifier = Modifier.weight(1f)
        )
        KategoriTransaksiItem(
            iconRes = R.drawable.ic_pembelian_fill,
            category = "Pembelian",
            isSelected = selectedCategory == "Pembelian",
            onClick = {
                if (selectedCategory == "Pembelian") onCategorySelected("Semua")
                else onCategorySelected("Pembelian")
            },
            modifier = Modifier.weight(1f)
        )
        KategoriTransaksiItem(
            iconRes = R.drawable.ic_tagihan_fill,
            category = "Tagihan",
            isSelected = selectedCategory == "Tagihan",
            onClick = {
                if (selectedCategory == "Tagihan") onCategorySelected("Semua")
                else onCategorySelected("Tagihan")
            },
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun KategoriTransaksiItem(
    iconRes: Int,
    category: String,
    isSelected: Boolean = false,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}
) {
    Row (
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(
                if (isSelected) Primary else Primary.copy(0.1f)
            )
            .clickable { onClick() },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = if (isSelected) White else Primary,
            modifier = Modifier.height(14.dp)
        )
        Spacer(Modifier.width(4.dp))
        AppText(
            text = category,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = if (isSelected) White else Primary
        )
    }
}