package com.example.ppab_responsi1_kelompok09.presentation.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetContactsUseCase

class ContactViewModelFactory(
    private val getContactsUseCase: GetContactsUseCase,
    private val token: String
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ContactViewModel::class.java)) {
            return ContactViewModel(getContactsUseCase, token) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}