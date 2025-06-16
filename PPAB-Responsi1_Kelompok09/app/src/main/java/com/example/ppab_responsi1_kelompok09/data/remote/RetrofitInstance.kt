package com.example.ppab_responsi1_kelompok09.data.remote

import com.example.ppab_responsi1_kelompok09.data.local.TokenDataStore
import com.example.ppab_responsi1_kelompok09.data.remote.retrofit.ApiClient.retrofit
import com.example.ppab_responsi1_kelompok09.data.service.ProductApi
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.logging.HttpLoggingInterceptor

object RetrofitInstance {
    private const val BASE_URL = "http://10.0.2.2:8000/"

    val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: AuthApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApi::class.java)
    }

    val productApi : ProductApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ProductApi::class.java)
    }

}


//object RetrofitInstance {
//    private const val BASE_URL = "http://10.0.2.2:8000/" // atau sesuai dengan IP Laravel server-mu
//
//    private val client = OkHttpClient.Builder()
//        .addInterceptor { chain ->
//            val prefs = TokenDataStore// ambil SharedPreferences dari context
//            val token = prefs.getString("token", null)
//            val requestBuilder = chain.request().newBuilder()
//            if (!token.isNullOrEmpty()) {
//                requestBuilder.addHeader("Authorization", "Bearer $token")
//            }
//            chain.proceed(requestBuilder.build())
//        }
//        .build()
//
//    val api: AuthApi by lazy {
//        Retrofit.Builder()
//            .baseUrl(BASE_URL)
////            .client(client)
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//            .create(AuthApi::class.java)
//    }
//}
