package com.example.ppab_responsi1_kelompok09.domain.repository

import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.User

object UserRepository {
    private val user = listOf(
        User(
            id = "1",
            name = "Admin Aril",
            email = "admin@scaleup.com",
            password = "admin123", // sebaiknya disimpan terenkripsi, tapi ini untuk contoh
            profilePhoto = R.drawable.img_profile_picture,
            namaUsaha = "ScaleUp Central",
            nomorHandphone = "081234567890",
            tipeUsaha = "Teknologi",
            npwp = "1234567890",
            provinsi = "Jawa Tengah",
            kabupatenKota = "Surakarta",
            kecamatan = "Laweyan",
            desa = "Pajang"
        ),
        User(
            id = "2",
            name = "Budi Santoso",
            email = "budi@bisnis.com",
            password = "user456",
            profilePhoto = R.drawable.img_profile_picture,
            namaUsaha = "Toko Budi",
            nomorHandphone = "089876543210",
            tipeUsaha = "Ritel",
            npwp = "0987654321",
            provinsi = "Jawa Barat",
            kabupatenKota = "Bandung",
            kecamatan = "Coblong",
            desa = "Dago"
        )
    )

    fun getUserById(id : String): User? {
        return user.find { it.id == id }
    }
}