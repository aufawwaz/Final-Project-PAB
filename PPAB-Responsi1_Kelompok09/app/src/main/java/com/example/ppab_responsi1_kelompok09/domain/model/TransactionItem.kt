package com.example.ppab_responsi1_kelompok09.domain.model

class TransactionItem (
//    data pesanan tiap transaksi, btw product_id penerapannya pake integer
    val transactionId : String,
//    val productId : Int,
    val productId : String,
    val amount : Int,
)