package com.example.ppab_responsi1_kelompok09.domain.model

import com.example.ppab_responsi1_kelompok09.R

data class Contact(
    val imageRes : Int,
    val name : String,
    val number : String
)

val KontakPelanggan = listOf(
    Contact(R.drawable.img_profile_picture, "Aril Fadla Hudallah", "081234567890"),
    Contact(R.drawable.img_profile_picture, "Ariel Josua", "081234567890"),
    Contact(R.drawable.img_profile_picture, "Aufa Fawwaz Aryasatya", "081234567890"),
    Contact(R.drawable.img_profile_picture, "Aril Fadla Hudallah", "081234567890"),
    Contact(R.drawable.img_profile_picture, "Ariel Josua", "081234567890"),
    Contact(R.drawable.img_profile_picture, "Aufa Fawwaz Aryasatya", "081234567890"),
    Contact(R.drawable.img_profile_picture, "Aril Fadla Hudallah", "081234567890"),
    Contact(R.drawable.img_profile_picture, "Ariel Josua", "081234567890"),
    Contact(R.drawable.img_profile_picture, "Aufa Fawwaz Aryasatya", "081234567890")
)
val KontakPenjual = listOf(
    Contact(R.drawable.img_profile_picture, "Budiman Santoso", "081234567890"),
    Contact(R.drawable.img_profile_picture, "PT Bank Central", "081234567890"),
    Contact(R.drawable.img_profile_picture, "PT Bumi Makmur", "081234567890"),
    Contact(R.drawable.img_profile_picture, "Budiman Santoso", "081234567890"),
    Contact(R.drawable.img_profile_picture, "PT Bank Central", "081234567890"),
    Contact(R.drawable.img_profile_picture, "PT Bumi Makmur", "081234567890")
)