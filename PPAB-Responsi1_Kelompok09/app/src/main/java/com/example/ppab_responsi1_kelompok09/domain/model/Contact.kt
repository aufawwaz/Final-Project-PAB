package com.example.ppab_responsi1_kelompok09.domain.model

data class Contact(
    val id: Long,
    val nama_kontak: String,
    val nomor_handphone: String?,
    val image_kontak: String?,
    val email_kontak: String?,
    val alamat_kontak: String?,
    val jumlah_transaksi: Int,
    val user_id: Long,
    val created_at: String,
    val updated_at: String
)

data class DummyContact(
    val id: String,
    val nama_kontak: String,
    val nomor_kontak: String,
    val image_kontak: Int,
    val email_kontak: String,
    val alamat_kontak: String,
    val jumlah_transaksi: Int,
)