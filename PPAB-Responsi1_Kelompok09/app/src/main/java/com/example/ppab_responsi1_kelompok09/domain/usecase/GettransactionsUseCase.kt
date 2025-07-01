package com.example.ppab_responsi1_kelompok09.domain.usecase

import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionByIdResponse
import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionResponse
import com.example.ppab_responsi1_kelompok09.data.repository.TransactionRepository

class GetTransactionsUseCase (
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(token: String, page: Int) : TransactionResponse {
        return repository.getTransactions(token, page)
    }
}

class GetBillUseCase (
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(token: String, id : String) : TransactionByIdResponse {
        return repository.getTransactionById(token, id)
    }
}
class GetSaleUseCase (
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(token: String, id : String) : TransactionByIdResponse {
        return repository.getTransactionById(token, id)
    }
}
class GetPurchaseUseCase (
    private val repository: TransactionRepository
) {
    suspend operator fun invoke(token: String, id : String) : TransactionByIdResponse {
        return repository.getTransactionById(token, id)
    }
}