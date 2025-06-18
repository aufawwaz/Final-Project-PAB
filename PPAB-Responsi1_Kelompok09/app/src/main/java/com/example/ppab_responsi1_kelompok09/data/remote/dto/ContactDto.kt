package com.example.ppab_responsi1_kelompok09.data.remote.dto

import com.google.gson.annotations.SerializedName

data class ContactDto(
    val id: String,
    val nama_kontak: String,
    @SerializedName("nomor_handphone") val nomor_kontak: String,
    val image_kontak: String?,
    val email_kontak: String,
    val alamat_kontak: String,
    val jumlah_transaksi: Int
)