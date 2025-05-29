package com.example.ppab_responsi1_kelompok09.domain.model

import com.example.ppab_responsi1_kelompok09.R

data class ProductItem (
    val onCLick : () -> Unit,
    val productImage : Int,
    val category : String,
    val productName : String,
    val sold : Int,
    val stock : Int,
    val price : Int
)

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