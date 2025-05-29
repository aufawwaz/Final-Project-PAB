package com.example.ppab_responsi1_kelompok09.domain.model

data class ProductItem (
    val onCLick : () -> Unit,
    val productImage : Int,
    val category : String,
    val productName : String,
    val sold : Int,
    val stock : Int,
    val price : Int
)
