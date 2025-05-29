package com.example.ppab_responsi1_kelompok09.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val username : String,
    val email : String,
    val password : String
)
