package com.example.ppab_responsi1_kelompok09.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.domain.model.News
import com.example.ppab_responsi1_kelompok09.domain.repository.NewsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class NewsViewModel(
    private val limit: Int = 21,

) : ViewModel() {
    private val _newsList = MutableStateFlow<List<News>>(emptyList())
    val newsList: StateFlow<List<News>> = _newsList

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMsg = MutableStateFlow<String?>(null)
    val errorMsg: StateFlow<String?> = _errorMsg

    init {
        fetchNews(limit)
    }

    private fun fetchNews(limit : Int = 21) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMsg.value = null
            try {
                val result = NewsRepository.getAll(limit)
                if(result.isEmpty()){
                    _errorMsg.value = "Gagal memuat berita"
                } else {
                    _newsList.value = result
                }
            } catch (e: Exception) {
                _errorMsg.value = "Gagal memuat berita"
            } finally {
                _isLoading.value = false
            }
        }
    }
}