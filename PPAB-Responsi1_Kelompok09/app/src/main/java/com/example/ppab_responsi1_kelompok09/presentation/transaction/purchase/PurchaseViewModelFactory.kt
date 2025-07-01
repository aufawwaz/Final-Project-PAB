package com.example.ppab_responsi1_kelompok09.presentation.transaction.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetPurchaseUseCase
import com.example.ppab_responsi1_kelompok09.presentation.transaction.purchase.PurchaseViewModel

class PurchaseViewModelFactory (
    private val getPurchaseUseCase : GetPurchaseUseCase,
    private val token: String,
    private val id: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(PurchaseViewModel::class.java)) {
            return PurchaseViewModel(getPurchaseUseCase, token, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}