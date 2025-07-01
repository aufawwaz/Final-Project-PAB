package com.example.ppab_responsi1_kelompok09.presentation.transaction.sale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetSaleUseCase

class SaleViewModelFactory (
    private val getSaleUseCase : GetSaleUseCase,
    private val token: String,
    private val id: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SaleViewModel::class.java)) {
            return SaleViewModel(getSaleUseCase, token, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}