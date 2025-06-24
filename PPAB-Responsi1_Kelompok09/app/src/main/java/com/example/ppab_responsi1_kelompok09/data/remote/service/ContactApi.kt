package com.example.ppab_responsi1_kelompok09.data.remote.service

import com.example.ppab_responsi1_kelompok09.data.remote.dto.ContactDetailResponse
import com.example.ppab_responsi1_kelompok09.data.remote.dto.ContactResponse
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path
import retrofit2.http.Query

interface ContactApi {
    @GET("api/contact")
    suspend fun getContacts(
        @Header("Authorization") token: String,
        @Query("page") page: Int = 1
    ): ContactResponse

    @GET("api/contact/{id}")
    suspend fun getContactDetail(
        @Header("Authorization") token: String,
        @Path("id") id: Int
    ): ContactDetailResponse
}