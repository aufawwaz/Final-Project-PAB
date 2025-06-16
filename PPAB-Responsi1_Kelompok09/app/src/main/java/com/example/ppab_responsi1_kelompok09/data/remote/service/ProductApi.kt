package com.example.ppab_responsi1_kelompok09.data.service

import com.example.ppab_responsi1_kelompok09.data.remote.dto.ProductDto
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

interface ProductApi {
    @GET("api/product")
    suspend fun getAllProducts(@Header("Authorization") token: String): ProductResponse

    @GET("product/{id}")
    suspend fun getProductById(
        @Header("Authorization") token: String,
        @Path("id") id: String
    ): ProductDetailResponse

}

data class ProductResponse(
    val data: List<ProductDto>
)

data class ProductDetailResponse(
    val data: ProductDto
)