package com.example.ppab_responsi1_kelompok09.data.mapper

import com.example.ppab_responsi1_kelompok09.data.remote.dto.UserDto
import com.example.ppab_responsi1_kelompok09.domain.model.User

fun UserDto.toDomain(): User = User(
    id = id ?: 0,
    name = name ?: "",
    email = email ?: "",
    profilePhoto = profilePhoto ?: "",
    namaUsaha = namaUsaha ?: "",
    nomorHandphone = nomorHandphone ?: "",
    tipeUsaha = tipeUsaha ?: "",
    npwp = npwp ?: "",
    provinsi = provinsi ?: "",
    kabupatenKota = kabupatenKota ?: "",
    kecamatan = kecamatan ?: "",
    desa = desa ?: ""
)
