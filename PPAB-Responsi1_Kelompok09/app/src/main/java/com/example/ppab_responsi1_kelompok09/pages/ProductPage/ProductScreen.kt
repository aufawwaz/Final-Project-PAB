package com.example.ppab_responsi1_kelompok09.pages.ProductPage

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.runtime.Composable
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
import com.example.ppab_responsi1_kelompok09.common.component.BottomSpacer
import com.example.ppab_responsi1_kelompok09.common.component.CustomButton
import com.example.ppab_responsi1_kelompok09.common.component.HeaderBox
import com.example.ppab_responsi1_kelompok09.common.component.HeaderGradient
import com.example.ppab_responsi1_kelompok09.common.component.InputTextForm
import com.example.ppab_responsi1_kelompok09.common.component.PageHeader
import com.example.ppab_responsi1_kelompok09.common.component.ProductCard
import com.example.ppab_responsi1_kelompok09.common.style.AppText
import com.example.ppab_responsi1_kelompok09.common.style.PageTextHeader
import com.example.ppab_responsi1_kelompok09.common.style.dropShadow200
import com.example.ppab_responsi1_kelompok09.data.ProductItem
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Preview
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
                iconRes = R.drawable.produk_fill,
                description = "10 Produk"
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                KategoriSatuanSection(navController)
                SearchBarFilterSection()
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
                        painter = painterResource(R.drawable.produk_fill),
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
            iconRes = R.drawable.home_fill,
            text = "Kategori"
        )
        KategoriSatuanItem(
            navController,
            modifier = Modifier.weight(1f),
            iconRes = R.drawable.home_fill,
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
            painter = painterResource(R.drawable.next),
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.size(12.dp)
        )
    }
}

@Composable
private fun SearchBarFilterSection() {
    Row (
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height(44.dp)
    ) {
        Box (
            modifier = Modifier
                .weight(1f)
                .fillMaxHeight()
        ) {
            InputTextForm(
                text = "Search",
                icon = R.drawable.search,
                isPassword = false,
                isLost = true
            )
        }
        Box(
            modifier = Modifier
                .size(44.dp)
                .border(1.dp, Gray, RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.Center
        ) {
            Icon (
                painter = painterResource(R.drawable.home_fill),
                contentDescription = null,
                modifier = Modifier.width(16.dp),
                tint = Gray
            )
        }
    }
}

@Composable
private fun ProductGrid () {
    var productList = listOf(
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", "100", "200", "Rp1.548.000"),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", "100", "200", "Rp1.548.000"),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", "100", "200", "Rp1.548.000"),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", "100", "200", "Rp1.548.000"),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", "100", "200", "Rp1.548.000"),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", "100", "200", "Rp1.548.000"),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", "100", "200", "Rp1.548.000"),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", "100", "200", "Rp1.548.000"),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", "100", "200", "Rp1.548.000"),
        ProductItem({}, R.drawable.img_product_1, "MEN SHOES", "Air Jordan 1 Mid", "100", "200", "Rp1.548.000"),
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
