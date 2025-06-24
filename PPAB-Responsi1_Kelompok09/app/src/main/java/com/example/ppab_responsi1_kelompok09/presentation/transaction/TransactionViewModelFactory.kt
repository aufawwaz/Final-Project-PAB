package com.example.ppab_responsi1_kelompok09.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetTransactionsUseCase

class TransactionViewModelFactory (
    private val getTransactionsUseCase : GetTransactionsUseCase,
    private val token: String
    ) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
                return TransactionViewModel(getTransactionsUseCase, token) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
}