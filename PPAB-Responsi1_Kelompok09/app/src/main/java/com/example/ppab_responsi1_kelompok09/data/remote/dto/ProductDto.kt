package com.example.ppab_responsi1_kelompok09.data.remote.dto

data class ProductDto(
    val id: Int,
    val nama_produk: String,
    val slug: String,
    val image: String,
    val satuan: String,
    val harga_jual: Int,
    val harga_modal: Int,
    val kategori: String,
    val deskripsi: String,
    val stok: Int,
    val user_id: Int
)
