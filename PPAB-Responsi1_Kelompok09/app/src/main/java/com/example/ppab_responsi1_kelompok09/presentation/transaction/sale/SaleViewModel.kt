package com.example.ppab_responsi1_kelompok09.presentation.transaction.sale

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.data.mapper.toDomain
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetSaleUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class SaleViewModel (
    private val getSaleUseCase: GetSaleUseCase,
    private val token: String,
    id: String,
) : ViewModel() {
    private val _sale = MutableStateFlow<Transaction.Sell?>(null)
    val transaction: StateFlow<Transaction.Sell?> = _sale

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
                val response = getSaleUseCase(token, id)
                val transaction = response.data.toDomain()
                val sale = transaction as? Transaction.Sell
                if (sale == null) {
                    _error.value = "Transaction is not a sale"
                }
                _sale.value = sale
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}