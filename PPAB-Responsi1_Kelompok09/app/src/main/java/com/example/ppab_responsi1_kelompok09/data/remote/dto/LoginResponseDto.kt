package com.example.ppab_responsi1_kelompok09.data.remote.dto

import com.google.gson.annotations.SerializedName

data class LoginResponseDto(
    val message: String,
    val token: String,
    val user: UserDto
)