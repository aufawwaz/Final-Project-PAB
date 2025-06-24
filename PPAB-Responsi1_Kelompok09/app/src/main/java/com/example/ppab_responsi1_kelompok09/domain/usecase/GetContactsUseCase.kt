package com.example.ppab_responsi1_kelompok09.domain.usecase

import com.example.ppab_responsi1_kelompok09.data.remote.dto.ContactResponse
import com.example.ppab_responsi1_kelompok09.data.repository.ContactRepository

class GetContactsUseCase (
    private val repository: ContactRepository
) {
    suspend operator fun invoke(token: String, page: Int) : ContactResponse {
        return repository.getContacts(token, page)
    }
}