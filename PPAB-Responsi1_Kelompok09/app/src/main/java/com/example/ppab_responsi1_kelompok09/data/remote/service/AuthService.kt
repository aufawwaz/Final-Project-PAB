package com.example.ppab_responsi1_kelompok09.data.remote.service

import com.example.ppab_responsi1_kelompok09.domain.model.LoginRequest
import com.example.ppab_responsi1_kelompok09.domain.model.LoginResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface AuthService {
    @POST("api/login")
    fun login(@Body request: LoginRequest): Call<LoginResponse>
}