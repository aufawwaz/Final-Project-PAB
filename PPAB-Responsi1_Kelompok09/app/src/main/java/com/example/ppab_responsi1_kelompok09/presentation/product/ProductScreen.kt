package com.example.ppab_responsi1_kelompok09.presentation.product

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.Product
import com.example.ppab_responsi1_kelompok09.domain.repository.ProductRepository
import com.example.ppab_responsi1_kelompok09.presentation.components.BottomSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomButton
import com.example.ppab_responsi1_kelompok09.presentation.components.PageHeader
import com.example.ppab_responsi1_kelompok09.presentation.components.ProductCard
import com.example.ppab_responsi1_kelompok09.presentation.components.SearchBarFilter
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import coil.compose.AsyncImage
import java.math.BigDecimal

@Composable
fun ProductScreen(navController: NavController = rememberNavController(), token: String) {
//    val product = ProductRepository.getAllProducts()
    val productViewModel: ProductViewModel = viewModel()
    val products by productViewModel.products.collectAsState()

    var searchQuery by rememberSaveable { mutableStateOf("") }
    // State untuk filter kategori & satuan
    var selectedCategory by rememberSaveable { mutableStateOf("Semua") }
    var selectedSatuan by rememberSaveable { mutableStateOf("Semua") }
    var showCategoryDialog by remember { mutableStateOf(false) }
    var showSatuanDialog by remember { mutableStateOf(false) }

    // Ambil semua kategori & satuan unik dari produk
    val allCategories = listOf("Semua") + products.map { it.category }.distinct()
    val allSatuan = listOf("Semua") + products.map { it.satuan }.distinct()

    // Filter produk sesuai search, kategori, dan satuan
    val filteredProducts = products.filter {
        (searchQuery.isBlank() || it.productName.contains(searchQuery, ignoreCase = true)) &&
        (selectedCategory == "Semua" || it.category == selectedCategory) &&
        (selectedSatuan == "Semua" || it.satuan == selectedSatuan)
    }

    LaunchedEffect(token) {
        println("DEBUG: ProductScreen token = $token")
        productViewModel.fetchProducts(token)
    }

    val listState = rememberLazyListState()
    val isSticky = listState.firstVisibleItemIndex > 0
    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .background(White),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                PageHeader(
                    pagetitle = "Produk",
                    title = "Total Produk",
                    iconRes = R.drawable.ic_produk_fill,
                    description = filteredProducts.size.toString() + " Produk"
                )
                KategoriSatuanSection(
                    selectedCategory = selectedCategory,
                    selectedSatuan = selectedSatuan,
                    onCategoryClick = { showCategoryDialog = true },
                    onSatuanClick = { showSatuanDialog = true }
                )
                SearchBarFilter(Modifier.padding(top = 16.dp), "Cari Produk", onSearch = { searchQuery = it })
            }
            itemsIndexed(filteredProducts.chunked(2)) { _, rowItems ->
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    for ((index, item) in rowItems.withIndex()) {
                        ProductCard(
                            onCLick = { navController.navigate("product_detail/${item.id}") },
                            productImage = item.productImage,
                            category = item.category,
                            productName = item.productName,
                            sold = item.sold,
                            stock = item.stock,
                            price = item.price,
                            modifier = Modifier.weight(1f)
                        )
                        if (index == 0 && rowItems.size == 1) {
                            Spacer(modifier = Modifier.weight(1f))
                        }
                    }
                }
            }
            item { BottomSpacer() }
        }
        // Dialog Kategori
        if (showCategoryDialog) {
            androidx.compose.ui.window.Dialog(onDismissRequest = { showCategoryDialog = false }) {
                Box(
                    modifier = Modifier
                        .width(316.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(White)
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .height(44.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            AppText(
                                text = "Pilih Kategori",
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                        allCategories.forEach { cat ->
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        selectedCategory = cat
                                        showCategoryDialog = false
                                    }
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                                    .fillMaxWidth()
                            ) {
                                AppText(
                                    text = cat,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
        // Dialog Satuan
        if (showSatuanDialog) {
            androidx.compose.ui.window.Dialog(onDismissRequest = { showSatuanDialog = false }) {
                Box(
                    modifier = Modifier
                        .width(316.dp)
                        .clip(RoundedCornerShape(16.dp))
                        .background(White)
                        .padding(vertical = 16.dp)
                ) {
                    Column(
                        horizontalAlignment = Alignment.Start,
                        verticalArrangement = Arrangement.spacedBy(16.dp),
                    ) {
                        Box(
                            modifier = Modifier
                                .height(44.dp)
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            AppText(
                                text = "Pilih Satuan",
                                fontWeight = FontWeight.Medium,
                                fontSize = 16.sp
                            )
                        }
                        allSatuan.forEach { satuan ->
                            Column(
                                modifier = Modifier
                                    .clickable {
                                        selectedSatuan = satuan
                                        showSatuanDialog = false
                                    }
                                    .padding(horizontal = 16.dp, vertical = 4.dp)
                                    .fillMaxWidth()
                            ) {
                                AppText(
                                    text = satuan,
                                    fontWeight = FontWeight.Normal,
                                    fontSize = 14.sp
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun KategoriSatuanSection(
    selectedCategory: String,
    selectedSatuan: String,
    onCategoryClick: () -> Unit,
    onSatuanClick: () -> Unit,
) {
    Row (
        modifier = Modifier
            .padding(top = 16.dp)
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        KategoriSatuanItem(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.ic_kategori_fill,
            text = "Kategori",
            isSelected = selectedCategory != "Semua",
            onClick = onCategoryClick
        )
        KategoriSatuanItem(
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.ic_satuan_fill,
            text = "Satuan",
            isSelected = selectedSatuan != "Semua",
            onClick = onSatuanClick
        )
    }
}

@Composable
private fun KategoriSatuanItem (
    modifier: Modifier = Modifier,
    iconRes : Int,
    text : String,
    isSelected: Boolean = false,
    onClick: () -> Unit
) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .clickable { onClick() }
            .background(
                if (isSelected) Primary else Primary.copy(0.1f)
            )
            .height(44.dp)
            .padding(horizontal = 16.dp)
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon (
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = if (isSelected) White else Primary,
                modifier = Modifier.size(12.dp)
            )
            AppText(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = if (isSelected) White else Primary
            )
        }
        Icon (
            painter = painterResource(R.drawable.ic_next),
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(12.dp)
        )
    }
}
