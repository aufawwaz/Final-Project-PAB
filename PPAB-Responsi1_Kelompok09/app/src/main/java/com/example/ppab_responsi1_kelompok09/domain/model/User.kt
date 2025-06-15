package com.example.ppab_responsi1_kelompok09.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: String,
    val name: String,
    val email: String,
    val password: String,
    val profilePhoto: Int?,
    val namaUsaha: String?,
    val nomorHandphone: String?,
    val tipeUsaha: String?,
    val npwp: String?,
    val provinsi: String?,
    val kabupatenKota: String?,
    val kecamatan: String?,
    val desa: String?
)
