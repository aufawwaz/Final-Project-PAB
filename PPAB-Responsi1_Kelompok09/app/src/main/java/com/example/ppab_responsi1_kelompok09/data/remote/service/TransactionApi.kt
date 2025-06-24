package com.example.ppab_responsi1_kelompok09.data.remote.service

import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionDto
import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Query

interface TransactionApi {
    @GET("api/transaction")
    suspend fun getTransactions(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1
    ): TransactionResponse

    //NOT IMPLEMENTED YET
   @GET("api/transaction/{id}")
    suspend fun getTransactionDetail(
        @Header("Authorization") token: String,
        @Query("id") id: String
    ): TransactionDto
}

