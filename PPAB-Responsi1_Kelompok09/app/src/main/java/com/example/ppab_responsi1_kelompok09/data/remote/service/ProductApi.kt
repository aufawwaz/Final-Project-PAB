package com.example.ppab_responsi1_kelompok09.data.service

import com.example.ppab_responsi1_kelompok09.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Header

interface ProductApi {
    @GET("api/product")
    suspend fun getAllProducts(@Header("Authorization") token: String): ProductResponse
}

data class ProductResponse(
    val data: List<ProductDto>
)