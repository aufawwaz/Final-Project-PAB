package com.example.ppab_responsi1_kelompok09.data.repository

import com.example.ppab_responsi1_kelompok09.data.remote.dto.ContactDetailResponse
import com.example.ppab_responsi1_kelompok09.data.remote.dto.ContactResponse

interface ContactRepository {
    suspend fun getContacts(token: String, page: Int = 1): ContactResponse
    suspend fun getContactDetail(token: String, id: Int): ContactDetailResponse
}