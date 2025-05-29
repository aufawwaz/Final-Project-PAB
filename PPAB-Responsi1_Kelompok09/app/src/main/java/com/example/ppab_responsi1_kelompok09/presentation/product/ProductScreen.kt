package com.example.ppab_responsi1_kelompok09.presentation.product

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.presentation.components.BottomSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomButton
import com.example.ppab_responsi1_kelompok09.presentation.components.PageHeader
import com.example.ppab_responsi1_kelompok09.presentation.components.ProductCard
import com.example.ppab_responsi1_kelompok09.presentation.components.SearchBarFilter
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.domain.model.ProductItem
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun ProductScreen(navController: NavController = rememberNavController()) {
    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PageHeader(
                pagetitle = "Produk",
                title = "Total Produk",
                iconRes = R.drawable.ic_produk_fill,
                description = "10 Produk"
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                KategoriSatuanSection(navController)
                SearchBarFilter("Cari Produk")
                ProductGrid()
                BottomSpacer()
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = -(12.dp), y = -(138.dp))
                .width(180.dp)
        ) {
            CustomButton({ }, {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_add_box),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    AppText("Tambah Produk", 12.sp, color = White)
                }
            })
        }
    }
}

@Composable
private fun KategoriSatuanSection(navController: NavController) {
    Row (
        modifier = Modifier
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        KategoriSatuanItem(
            navController,
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.ic_kategori_fill,
            text = "Kategori"
        )
        KategoriSatuanItem(
            navController,
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.ic_satuan_fill,
            text = "Satuan"
        )
    }
}

@Composable
private fun KategoriSatuanItem (
    navController: NavController,
    modifier: Modifier = Modifier,
    iconRes : Int,
    text : String
) {
    Row (
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(Primary.copy(0.1f))
            .height(44.dp)
            .padding(horizontal = 16.dp)
            .clickable{}
    ) {
        Row (
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon (
                painter = painterResource(iconRes),
                contentDescription = null,
                tint = Primary,
                modifier = Modifier.size(12.dp)
            )
            AppText(
                text = text,
                fontWeight = FontWeight.SemiBold,
                fontSize = 12.sp,
                color = Primary
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

@Composable
private fun ProductGrid () {
    var productList = listOf(
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", 100, 200, 1548000),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", 100, 20, 1548000),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", 100, 0, 1548000),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", 100, 9, 1548000),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", 100, 200, 1548000),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", 100, 10, 1548000),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", 100, 200, 1548000),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", 100, 200, 1548000),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", 100, 200, 1548000),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", 100, 200, 1548000),
    )

    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = Modifier
            .fillMaxSize()
            .height((productList.size * 264/2).dp + 20.dp),
        userScrollEnabled = false,
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items (productList.size) { i ->
            val item = productList[i]

            ProductCard(
                onCLick = item.onCLick,
                productImage = item.productImage,
                category = item.category,
                productName = item.productName,
                sold = item.sold,
                stock = item.stock,
                price = item.price
            )
        }
    }
}
