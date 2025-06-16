package com.example.ppab_responsi1_kelompok09.presentation.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.domain.repository.ProductRepository
import com.example.ppab_responsi1_kelompok09.domain.model.Product
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow

data class ProductDetailState(
    val isLoading: Boolean = false,
    val product: Product? = null,
    val error: String? = null
)

class ProductViewModel : ViewModel() {
    private val repository = ProductRepository()

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products = _products.asStateFlow()

    private val _state = MutableStateFlow(ProductDetailState())
    val state: StateFlow<ProductDetailState> = _state

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

    fun fetchProductDetail(id: String, token: String) {
        viewModelScope.launch {
            try {
                _state.value = state.value.copy(isLoading = true)
                val product = repository.getProductById(id, token)
                println("DEBUG: Product fetched = $product")
                _state.value = state.value.copy(product = product, isLoading = false)
            } catch (e: Exception) {
                println("DEBUG: Error fetching product = ${e.message}")
                _state.value = state.value.copy(error = e.message ?: "Unknown error", isLoading = false)
            }
        }
    }
}