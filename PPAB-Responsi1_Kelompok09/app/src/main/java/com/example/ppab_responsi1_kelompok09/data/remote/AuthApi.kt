package com.example.ppab_responsi1_kelompok09.data.remote

import com.example.ppab_responsi1_kelompok09.data.remote.dto.LoginResponseDto
import com.example.ppab_responsi1_kelompok09.data.remote.dto.ProfileResponseDto
import com.example.ppab_responsi1_kelompok09.domain.model.LoginRequest
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @POST("api/login")
    suspend fun login(@Body loginRequest: LoginRequest): LoginResponseDto

    @GET("api/profile")
    suspend fun getProfile(@Header("Authorization") token: String): ProfileResponseDto
}