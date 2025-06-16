package com.example.ppab_responsi1_kelompok09.data.mapper

import com.example.ppab_responsi1_kelompok09.data.remote.dto.ProductDto
import com.example.ppab_responsi1_kelompok09.domain.model.Product
import java.math.BigDecimal


fun ProductDto.toProduct(): Product {
    val baseUrl = "http://10.0.2.2:8000/"

    val imageUrl = if (image.startsWith("http")) {
        image
    } else {
        // Pastikan tidak ada ganda "storage" atau leading slash
        val normalizedImage = image.removePrefix("/").removePrefix("storage/")
        baseUrl + "storage/" + normalizedImage
    }

    return Product(
        id = id.toString(),
        productName = nama_produk,
        productImage = imageUrl,
        category = kategori,
        satuan = satuan,
        price = BigDecimal(harga_jual),
        modal = BigDecimal(harga_modal),
        productDescription = deskripsi,
        sold = 0,
        stock = stok.toLong()
    )
}

