package com.example.ppab_responsi1_kelompok09.data.repository

import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionResponse
import com.example.ppab_responsi1_kelompok09.data.remote.service.TransactionApi

class TransactionRepositoryImpl (
    private val transactionApi: TransactionApi
) : TransactionRepository {
    override suspend fun getTransactions(token: String, page: Int): TransactionResponse {
        return transactionApi.getTransactions("Bearer $token", page)
    }
}