package com.example.ppab_responsi1_kelompok09.data.mapper

import com.example.ppab_responsi1_kelompok09.data.remote.dto.ProductDto
import com.example.ppab_responsi1_kelompok09.domain.model.Product
import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class ProductDetailResponse(
    @SerializedName("data")
    val data: ProductDto
)

data class ProductDto(
    @SerializedName("id") val id: Int,
    @SerializedName("nama_produk") val namaProduk: String,
    @SerializedName("kategori") val kategori: String,
    @SerializedName("deskripsi") val deskripsi: String,
    @SerializedName("harga_jual") val hargaJual: Int,
    @SerializedName("harga_modal") val hargaModal: Int,
    @SerializedName("satuan") val satuan: String,
    @SerializedName("stok") val stok: Int,
    @SerializedName("image") val image: String,
    @SerializedName("slug") val slug: String,
    @SerializedName("user_id") val userId: Int,
    @SerializedName("created_at") val createdAt: String?,
    @SerializedName("updated_at") val updatedAt: String?
)


fun ProductDto.toProduct(): Product {
    val baseUrl = "http://192.168.100.192:8000/"

    val cleanImage = image.replace("\\", "/") // Ganti backslash dengan slash
    val path = if (cleanImage.startsWith("storage/")) cleanImage else "storage/$cleanImage"
    val imageUrl = baseUrl + path.removePrefix("/")

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
