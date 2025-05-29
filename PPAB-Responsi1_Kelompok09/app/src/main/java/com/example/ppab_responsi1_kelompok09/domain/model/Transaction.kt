package com.example.ppab_responsi1_kelompok09.domain.model

sealed class Transaction{
    data class Sell(
        val id : String,
        val customer : String,
        val date : String,
        val paymentMethod : String,
        val total : String
    ) : Transaction()

    data class Purchase(
        val id : String,
        val seller : String,
        val date : String,
        val total : String
    ) : Transaction()

    data class Bill(
        val id : String,
        val customer : String,
        val date : String,
        val status : String,
        val total : String
    ) : Transaction()
}
