package com.example.ppab_responsi1_kelompok09.data.remote.service

import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionItemDto
import retrofit2.http.GET
import retrofit2.http.Header

interface TransactionItemApi {
    @GET("api/transaction-item")
    suspend fun getAllTransactionItem(@Header("Authorization") token: String): TransactionItemResponse
}

data class TransactionItemResponse(
    val data: List<TransactionItemDto>
)