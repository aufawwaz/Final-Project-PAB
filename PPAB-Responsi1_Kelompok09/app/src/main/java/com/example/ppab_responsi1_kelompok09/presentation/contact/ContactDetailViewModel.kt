package com.example.ppab_responsi1_kelompok09.presentation.contact

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ppab_responsi1_kelompok09.data.remote.dto.ContactDetailResponse
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetContactDetailUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ContactDetailViewModel(
    private val getContactDetailUseCase: GetContactDetailUseCase,
    private val token: String
) : ViewModel() {

    private val _contactDetail = MutableStateFlow<ContactDetailResponse?>(null)
    val contactDetail: StateFlow<ContactDetailResponse?> = _contactDetail

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun loadContactDetail(contactId: Int) {
        viewModelScope.launch {
            _loading.value = true
            _error.value = null
            try {
                val response = getContactDetailUseCase(token, contactId)
                _contactDetail.value = response
            } catch (e: Exception) {
                _error.value = e.message
            } finally {
                _loading.value = false
            }
        }
    }
}