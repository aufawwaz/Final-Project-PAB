package com.example.ppab_responsi1_kelompok09.domain.usecase

import com.example.ppab_responsi1_kelompok09.data.remote.dto.ContactDetailResponse
import com.example.ppab_responsi1_kelompok09.data.repository.ContactRepository

class GetContactDetailUseCase(
    private val repository: ContactRepository
) {
    suspend operator fun invoke(token: String, contactId: Int): ContactDetailResponse {
        return repository.getContactDetail(token, contactId)
    }
}