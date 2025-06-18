package com.example.ppab_responsi1_kelompok09.data.remote.service

import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionDto
import retrofit2.http.GET
import retrofit2.http.Header

interface TransactionApi {
    @GET("api/transaction")
    suspend fun getAllTransaction(@Header("Authorization") token: String): TransactionResponse
}

data class TransactionResponse(
    val success: Boolean,
    val data: List<TransactionDto>
)