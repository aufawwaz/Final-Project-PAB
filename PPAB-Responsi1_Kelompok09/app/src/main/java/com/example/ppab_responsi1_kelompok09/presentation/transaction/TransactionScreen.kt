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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.data.remote.RetrofitInstance
import com.example.ppab_responsi1_kelompok09.data.repository.TransactionRepositoryImpl
import com.example.ppab_responsi1_kelompok09.presentation.components.BottomSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.PageHeader
import com.example.ppab_responsi1_kelompok09.presentation.components.SearchBarFilter
import com.example.ppab_responsi1_kelompok09.presentation.components.TonalIcon
import com.example.ppab_responsi1_kelompok09.presentation.components.TransactionCard
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetTransactionsUseCase
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@Composable
fun TransactionScreen(
    navController: NavController = rememberNavController(),
    initialCategory: String = "Semua",
    token: String?
) {
    val viewModel: TransactionViewModel? = if (token != null) {
        val repository = remember { TransactionRepositoryImpl(RetrofitInstance.transactionApi) }
        val useCase = remember { GetTransactionsUseCase(repository) }
        viewModel(factory = TransactionViewModelFactory(useCase, token))
    } else null

    if (viewModel == null) {
        LoadingScreen()
        return
    }

    val transactions by viewModel.transaction.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    var selectedCategory by remember { mutableStateOf(initialCategory) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val filteredList = remember(transactions, selectedCategory, searchQuery) {
        transactions.let { list ->
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
                }
            }
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        when {
            loading -> LoadingScreen()
            !error.isNullOrEmpty() -> ErrorScreen()
            else -> {
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
                            description = transactions.size.toString() + " Transaksi"
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
                        }
                        val currentId = when (transactionItem) {
                            is Transaction.Sell -> transactionItem.id
                            is Transaction.Purchase -> transactionItem.id
                            is Transaction.Bill -> transactionItem.id
                        }
                        val detailRoute = when (transactionItem) {
                            is Transaction.Sell -> "penjualan_detail/$currentId"
                            is Transaction.Purchase -> "pembelian_detail/$currentId"
                            is Transaction.Bill -> "tagihan_detail/$currentId"
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

@Composable
private fun LoadingScreen(){
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
                description = "0 Transaksi"
            )
        }
        item {
            KategoriTransaksi(
                selectedCategory = "Semua",
                onCategorySelected = { }
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
            SearchBarFilter(Modifier, "Cari Transaksi", onSearch = {  })
        }
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
}

@Composable
private fun ErrorScreen(){
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
                description = "0 Transaksi"
            )
        }
        item {
            KategoriTransaksi(
                selectedCategory = "Semua",
                onCategorySelected = { }
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
            SearchBarFilter(Modifier, "Cari Transaksi", onSearch = {  })
        }
        item {
            Column(
                modifier = Modifier.height(300.dp).fillMaxWidth(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ){
                AppText(
//                    text = "Error: $error", //DEBUG PURPOSE
                    text = "Gagal memuat transaksi",
                    color = Color.Gray,
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center,
                )
            }
        }
    }
}