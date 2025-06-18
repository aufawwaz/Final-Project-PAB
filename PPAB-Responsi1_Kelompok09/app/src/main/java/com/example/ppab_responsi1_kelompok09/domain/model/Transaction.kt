package com.example.ppab_responsi1_kelompok09.domain.model

import java.math.BigDecimal
import java.util.Date

sealed class Transaction {
    data class Sell(
        val date: Date,
        val customer: DummyContact,
        val balance: Balance,
        val id: String,
        val paymentMethod: String,
        val total: BigDecimal
    ) : Transaction()

    data class Purchase(
        val date: Date,
        val supplier: DummyContact,
        val balance: Balance,
        val id: String,
        val status: String,
        val total: BigDecimal
    ) : Transaction()

    data class Bill(
        val date: Date,
        val outdate: Date,
        val customer: DummyContact,
        val balance: Balance,
        val id: String,
        val status: String,
        val total: BigDecimal
    ) : Transaction()
}