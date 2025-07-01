package com.example.ppab_responsi1_kelompok09.domain.model

import java.math.BigDecimal

class TransactionItem (
//    data pesanan tiap transaksi, btw product_id penerapannya pake integer
    val transactionId : String,
    val productId : String,
    val product : Product,
    val amount : Int,
)