package com.example.ppab_responsi1_kelompok09.domain.model

import java.math.BigDecimal

data class Balance(
    val id : String,
    val jenis : String,
    val nama : String,
    val saldo : BigDecimal
)