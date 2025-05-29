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

val transactionList = listOf(
    Transaction.Sell("TRSPJL11052025001", "Ariel Josua Simanjuntak", "19 Mei 2025", "Tunai", "5.194.000"),
    Transaction.Sell("TRSPJL11052025002", "Ariel Josua Simanjuntak", "19 Mei 2025", "QRIS", "5.194.000"),
    Transaction.Sell("TRSPJL11052025003", "Ariel Josua Simanjuntak", "19 Mei 2025", "Kartu Kredit", "5.194.000"),
    Transaction.Sell("TRSPJL11052025004", "Ariel Josua Simanjuntak", "19 Mei 2025", "Lainnya", "5.194.000"),
    Transaction.Purchase("TRSPBL11052025001", "PT Bumi Cermai", "19 Mei 2025", "290.000.000"),
    Transaction.Bill("TRSTGH11052025001", "Aufa Fawwaz Aryasatya", "19 Mei 2025", "Lunas", "5.194.000"),
    Transaction.Bill("TRSTGH11052025002", "Aril Fadla Hudallah", "19 Mei 2025", "Diproses", "5.194.000"),
    Transaction.Bill("TRSTGH11052025003", "PT Bumi Cermai", "19 Mei 2025", "Jatuh Tempo", "5.194.000")
)