package com.example.ppab_responsi1_kelompok09.domain.model

import java.math.BigDecimal

data class Product(
    val id: String,
    val productImage: String,
    val category: String,
    val satuan: String,
    val productName: String,
    val productDescription: String,
    val sold: Int,
    val stock: Long,
    val price: BigDecimal,
    val modal: BigDecimal = BigDecimal("0")
)