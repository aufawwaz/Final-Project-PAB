package com.example.ppab_responsi1_kelompok09.data.repository

import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionItemDto

interface TransactionItemRepository {
    suspend fun getTransactionItems(token: String, transactionId: String): List<TransactionItemDto>
}