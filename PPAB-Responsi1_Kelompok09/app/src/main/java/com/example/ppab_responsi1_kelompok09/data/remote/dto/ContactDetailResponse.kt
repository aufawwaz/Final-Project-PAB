package com.example.ppab_responsi1_kelompok09.data.remote.dto

import com.example.ppab_responsi1_kelompok09.domain.model.Contact

class ContactDetailResponse (
    val contact: Contact,
    val transaction: TransactionContactResponse
)

class TransactionContactResponse(

)