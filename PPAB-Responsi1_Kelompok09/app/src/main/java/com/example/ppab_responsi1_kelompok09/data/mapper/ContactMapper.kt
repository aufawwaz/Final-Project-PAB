package com.example.ppab_responsi1_kelompok09.data.mapper
//
//import com.example.ppab_responsi1_kelompok09.data.remote.retrofit.RetrofitInstance
//import com.example.ppab_responsi1_kelompok09.data.remote.retrofit.RetrofitInstance.BASE_URL
//import com.example.ppab_responsi1_kelompok09.data.remote.dto.ContactDto
//import com.example.ppab_responsi1_kelompok09.domain.model.Contact
//
//fun ContactDto.toContact(): Contact {
//    val imageUrl = if (image_kontak?.startsWith("http") == true) {
//        image_kontak
//    } else if (image_kontak != null) {
//        // Normalisasi path: hapus prefix "/", "storage/", dll
//        val normalizedImage = image_kontak.removePrefix("/").removePrefix("storage/")
//        RetrofitInstance.BASE_URL + "storage/" + normalizedImage
//    } else {
//        null
//    }
//
//    return Contact(
//        id = id,
//        nama_kontak = nama_kontak,
//        nomor_kontak = nomor_kontak,
//        image_kontak = imageUrl,
//        email_kontak = email_kontak,
//        alamat_kontak = alamat_kontak,
//        jumlah_transaksi = jumlah_transaksi
//    )
//}
//
