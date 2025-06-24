package com.example.ppab_responsi1_kelompok09.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Int,
    val name: String,
    val email: String,
    val profilePhoto: String?,
    val namaUsaha: String?,
    val nomorHandphone: String?,
    val tipeUsaha: String?,
    val npwp: String?,
    val provinsi: String?,
    val kabupatenKota: String?,
    val kecamatan: String?,
    val desa: String?
)
