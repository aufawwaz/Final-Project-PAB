package com.example.ppab_responsi1_kelompok09.presentation.news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class NewsViewModelFactory(
    private val limit: Int = 21
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
            return NewsViewModel(limit) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}