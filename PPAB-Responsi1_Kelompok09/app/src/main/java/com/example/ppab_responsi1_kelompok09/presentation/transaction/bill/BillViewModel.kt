package com.example.ppab_responsi1_kelompok09.presentation.transaction.bill

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.data.mapper.toDomain
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetBillUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class BillViewModel (
    private val getBillUseCase: GetBillUseCase,
    private val token: String,
    id: String,
) : ViewModel() {
    private val _bill = MutableStateFlow<Transaction.Bill?>(null)
    val transaction: StateFlow<Transaction.Bill?> = _bill

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
                val response = getBillUseCase(token, id)
                val transaction = response.data.toDomain()
                val bill = transaction as? Transaction.Bill
                if (bill == null) {
                    _error.value = "Transaction is not a bill"
                }
                _bill.value = bill
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}