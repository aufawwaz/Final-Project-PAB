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
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@Preview
@Composable
fun TransactionScreen(navController: NavController = rememberNavController(), initialCategory: String = "Semua") {
    // Ambil semua data transaksi
    val transaction = TransactionRepository.getAllTransaction()
    val category = navController.currentBackStackEntry
        ?.arguments?.getString("category") ?: "Semua"
    var selectedCategory by remember { mutableStateOf(category) }
    var searchQuery by rememberSaveable { mutableStateOf("") }

    val filteredList = when (selectedCategory) {
        "Penjualan" -> transaction.filterIsInstance<Transaction.Sell>()
        "Pembelian" -> transaction.filterIsInstance<Transaction.Purchase>()
        "Tagihan" -> transaction.filterIsInstance<Transaction.Bill>()
        else -> transaction
    }.filter {
        when (it) {
            is Transaction.Sell -> it.customer.nama_kontak.contains(searchQuery, ignoreCase = true)
            is Transaction.Purchase -> it.supplier.nama_kontak.contains(searchQuery, ignoreCase = true)
            is Transaction.Bill -> it.customer.nama_kontak.contains(searchQuery, ignoreCase = true)
        }
    }

    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                PageHeader(
                    pagetitle = "Transaksi",
                    title = "Total Transaksi",
                    iconRes = R.drawable.ic_transaksi_fill,
                    description = transaction.size.toString() + " Transaksi"
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
                Column (
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .dropShadow200(8.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(White)
                        .clickable { navController.navigate(detailRoute) }
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
                            .background(Gray.copy(0.3f))
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
//            CustomButton({ }, {
//                Row(
//                    modifier = Modifier.fillMaxWidth(),
//                    verticalAlignment = Alignment.CenterVertically
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.ic_transaksi_fill),
//                        contentDescription = null,
//                        modifier = Modifier.size(20.dp)
//                    )
//                    Spacer(Modifier.width(8.dp))
//                    AppText("Tambah Transaksi", 12.sp, color = White)
//                }
//            })
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
