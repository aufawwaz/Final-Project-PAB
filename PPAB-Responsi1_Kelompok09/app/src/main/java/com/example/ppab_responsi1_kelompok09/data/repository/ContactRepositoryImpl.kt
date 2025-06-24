package com.example.ppab_responsi1_kelompok09.data.repository

import com.example.ppab_responsi1_kelompok09.data.remote.dto.ContactDetailResponse
import com.example.ppab_responsi1_kelompok09.data.remote.dto.ContactResponse
import com.example.ppab_responsi1_kelompok09.data.remote.service.ContactApi

class ContactRepositoryImpl (
    private val contactApi: ContactApi
) : ContactRepository {
    override suspend fun getContacts(token: String, page: Int): ContactResponse {
        return contactApi.getContacts("Bearer $token", page)
    }

    override suspend fun getContactDetail(token: String, id: Int): ContactDetailResponse {
        return contactApi.getContactDetail("Bearer $token", id)
    }
}