package com.example.ppab_responsi1_kelompok09.data.mapper

import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionItemDto
import com.example.ppab_responsi1_kelompok09.data.remote.service.ProductApi
import com.example.ppab_responsi1_kelompok09.domain.model.TransactionItem

fun TransactionItemDto.toDomain(): TransactionItem {
    return TransactionItem(
        transactionId = transaction_id,
        productId = product_id.toString(),
        amount = jumlah,
        product = product.toProduct()
        )
}