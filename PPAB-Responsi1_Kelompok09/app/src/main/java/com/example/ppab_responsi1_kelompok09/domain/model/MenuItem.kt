package com.example.ppab_responsi1_kelompok09.domain.model

import com.example.ppab_responsi1_kelompok09.R

data class MenuItem(
    val label: String,
    val icon: Int,
    val onClick: () -> Unit = {}
)


