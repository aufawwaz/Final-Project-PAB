package com.example.ppab_responsi1_kelompok09.domain.model

data class Contact(
    val id: String,
    val nama_kontak : String,
    val nomor_kontak : String,
    val image_kontak : Int,
    val email_kontak : String,
    val alamat_kontak : String,
    val jumlah_transaksi : Int,
)
