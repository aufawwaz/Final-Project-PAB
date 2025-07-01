package com.example.ppab_responsi1_kelompok09.presentation.transaction.purchase

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.data.mapper.toDomain
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetPurchaseUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PurchaseViewModel (
    private val getPurchaseUseCase: GetPurchaseUseCase,
    private val token: String,
    id: String,
) : ViewModel() {
    private val _purchase = MutableStateFlow<Transaction.Purchase?>(null)
    val transaction: StateFlow<Transaction.Purchase?> = _purchase

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    init {
        fetchTransaction(id)
    }

    fun fetchTransaction(id: String) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = getPurchaseUseCase(token, id)
                val transaction = response.data.toDomain()
                val purchase = transaction as? Transaction.Purchase
                if (purchase == null) {
                    _error.value = "Transaction is not a Purchase"
                }
                _purchase.value = purchase
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}