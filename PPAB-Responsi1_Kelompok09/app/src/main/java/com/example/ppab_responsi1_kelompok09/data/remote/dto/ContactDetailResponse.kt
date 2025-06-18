package com.example.ppab_responsi1_kelompok09.data.remote.dto

import com.example.ppab_responsi1_kelompok09.domain.model.Contact
import java.util.Date

class ContactDetailResponse (
    val contact: Contact,
    val transactions: TransactionContactResponse?
)

class TransactionContactResponse(
    val id: String,
    val user_id: Int,
    val tanggal: String,
    val jenis: String,
    val kontak_id: Int,
    val saldo_id: Int,
    val nominal: String,
    val pembayaran: String,
    val status: String?,
    val jatuh_tempo: String?,
    val dibayar: String,
    val created_at: String,
    val updated_at: String
)