package com.example.ppab_responsi1_kelompok09.data.repository

import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionResponse


interface TransactionRepository {
    suspend fun getTransactions(token: String, page: Int = 1): TransactionResponse
}