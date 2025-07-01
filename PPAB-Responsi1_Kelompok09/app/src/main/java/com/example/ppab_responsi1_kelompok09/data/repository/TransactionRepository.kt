package com.example.ppab_responsi1_kelompok09.data.repository

import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionByIdResponse
import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionResponse

interface TransactionRepository {
    suspend fun getTransactions(token: String, page: Int = 1): TransactionResponse
    suspend fun getTransactionById(token: String, id: String): TransactionByIdResponse
}