package com.example.ppab_responsi1_kelompok09.presentation.transaction

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.data.mapper.toDomain
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetTransactionsUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TransactionViewModel (
    private val gettransactionsUseCase: GetTransactionsUseCase,
    private val token: String
) : ViewModel() {
    private val _transaction = MutableStateFlow<List<Transaction>>(emptyList())
    val transaction: StateFlow<List<Transaction>> = _transaction

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    private val _currentPage = MutableStateFlow(1)
    val currentPage: StateFlow<Int> = _currentPage

    private val _isLastPage = MutableStateFlow(false)
    val isLastPage: StateFlow<Boolean> = _isLastPage

    private val _totalTransaction = MutableStateFlow(0)
    val totalTransaction: StateFlow<Int> = _totalTransaction

    init {
        fetchTransaction(1)
    }

    fun fetchTransaction(page: Int) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = gettransactionsUseCase(token, page)
                val mappedData = response.data.map{ it.toDomain() }

                if (page == 1) {
                    _transaction.value = mappedData
                    _totalTransaction.value = response.total
                } else {
                    _transaction.value += mappedData
                }

                _currentPage.value = page
                _isLastPage.value = page >= response.last_page
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun fetchAllTransaction() {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val allData = mutableListOf<Transaction>()
                var page = 1
                var lastPage = 1

                do {
                    val response = gettransactionsUseCase(token, page)
                    allData.addAll(response.data.map { it.toDomain() })
                    lastPage = response.last_page
                    page++
                } while (page <= lastPage)

                _transaction.value = allData
                _currentPage.value = lastPage
                _isLastPage.value = true
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }

    fun loadNextPage() {
        if (_loading.value || _isLastPage.value) return
        val nextPage = _currentPage.value + 1
        fetchTransaction(nextPage)
    }

    fun refreshTransactions() {
        _transaction.value = emptyList()
        _currentPage.value = 1
        _isLastPage.value = false
        fetchTransaction(1)
    }
}