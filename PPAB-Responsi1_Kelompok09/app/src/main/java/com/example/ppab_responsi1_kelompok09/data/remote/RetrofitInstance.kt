package com.example.ppab_responsi1_kelompok09.data.remote

import com.example.ppab_responsi1_kelompok09.data.constant.BASE_URL
import com.example.ppab_responsi1_kelompok09.data.remote.service.ContactApi
import com.example.ppab_responsi1_kelompok09.data.local.TokenDataStore
import com.example.ppab_responsi1_kelompok09.data.remote.retrofit.ApiClient.retrofit
import com.example.ppab_responsi1_kelompok09.data.remote.service.TransactionApi
import com.example.ppab_responsi1_kelompok09.data.remote.service.TransactionItemApi
import com.example.ppab_responsi1_kelompok09.data.service.ProductApi
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
object RetrofitInstance {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val api: AuthApi by lazy {
        retrofit.create(AuthApi::class.java)
    }
    val productApi: ProductApi by lazy {
        retrofit.create(ProductApi::class.java)
    }
    val contactApi: ContactApi by lazy {
        retrofit.create(ContactApi::class.java)
    }

    val transactionApi: TransactionApi by lazy {
        retrofit.create(TransactionApi::class.java)
    }

    val transactionItemApi: TransactionItemApi by lazy {
        retrofit.create(TransactionItemApi::class.java)
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
