package com.example.ppab_responsi1_kelompok09.data.remote.dto

import com.google.gson.annotations.SerializedName

data class UserDto(
    val id: Int?,
    val name: String,
    val email: String,
    @SerializedName("profile_photo") val profilePhoto: String?,
    @SerializedName("nama_usaha") val namaUsaha: String?,
    @SerializedName("nomor_handphone") val nomorHandphone: String?,
    @SerializedName("tipe_usaha") val tipeUsaha: String?,
    val npwp: String?,
    val provinsi: String?,
    @SerializedName("kabupaten_kota") val kabupatenKota: String?,
    val kecamatan: String?,
    val desa: String?
)
