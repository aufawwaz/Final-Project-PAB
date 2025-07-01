package com.example.ppab_responsi1_kelompok09.presentation.transaction.bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetBillUseCase

class BillViewModelFactory (
    private val getBillUseCase : GetBillUseCase,
    private val token: String,
    private val id: String,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BillViewModel::class.java)) {
            return BillViewModel(getBillUseCase, token, id) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}