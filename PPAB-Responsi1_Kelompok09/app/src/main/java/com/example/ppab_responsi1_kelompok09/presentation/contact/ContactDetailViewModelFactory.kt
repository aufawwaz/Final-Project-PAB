package com.example.ppab_responsi1_kelompok09.presentation.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetContactDetailUseCase

class ContactDetailViewModelFactory(
    private val getContactDetailUseCase: GetContactDetailUseCase,
    private val token: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactDetailViewModel::class.java)) {
            return ContactDetailViewModel(getContactDetailUseCase, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}