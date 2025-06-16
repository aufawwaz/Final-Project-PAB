package com.example.ppab_responsi1_kelompok09.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.domain.repository.ProductRepository
import com.example.ppab_responsi1_kelompok09.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    fun fetchProducts(token: String) {
        viewModelScope.launch {
            try {
                val data = repository.getAllProducts(token)
                _products.value = data
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}