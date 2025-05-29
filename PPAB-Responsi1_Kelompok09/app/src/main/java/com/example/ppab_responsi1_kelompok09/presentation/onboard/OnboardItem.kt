package com.example.ppab_responsi1_kelompok09.presentation.onboard

import com.example.ppab_responsi1_kelompok09.R

data class OnboardItem(
    val title : String,
    val description : String,
    val image : Int
)

// List Onboarding Page
val OnboardList = listOf(
    OnboardItem(
        title = "Kelola Bisnis dalam Satu Tempat dengan Mudah",
        description = "Kelola produk, transaksi, saldo, dan pelanggan dengan mudah dalam satu platform.",
        image = R.drawable.img_onboard_1
    ),
    OnboardItem(
        title = "Pengetahuan adalah Kunci Bisnis Berkembang",
        description = "Pelajari dan temukan insight bisnis melalui Knowledge Card dan ambil keputusan dengan percaya diri.",
        image = R.drawable.img_onboard_2
    ),
    OnboardItem(
        title = "Catat & Kendalikan Keuanganmu Secara Real-Time",
        description = "Pantau saldo dan transaksi bisnis secara instan untuk pengelolaan yang lebih efisien.",
        image = R.drawable.img_onboard_3
    ),
)
