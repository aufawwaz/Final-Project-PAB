package com.example.ppab_responsi1_kelompok09.domain.repository

import com.example.ppab_responsi1_kelompok09.data.mapper.toDomain
import com.example.ppab_responsi1_kelompok09.data.remote.retrofit.RetrofitInstance
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.model.TransactionItem
import java.text.SimpleDateFormat
import java.math.BigDecimal
import java.util.Date
import java.util.Locale

object TransactionItemRepository {
    suspend fun getAllTransactionItems(token: String): List<TransactionItem> {
        val response = RetrofitInstance.transactionItemApi.getAllTransactionItem("Bearer $token")
        return response.data.map { it.toDomain() }
    }

    private val allData = listOf(
//        DUMMY DATAS
        TransactionItem("TRSPJL110625001", "PRD001", 2),
        TransactionItem("TRSPJL120625002", "PRD002", 2),
        TransactionItem("TRSPJL130625003", "PRD003", 2),
        TransactionItem("TRSPJL130625004", "PRD004", 2),
        TransactionItem("TRSPJL140625005", "PRD005", 2),

        TransactionItem("TRSPBL100625001", "PRD001", 2),
        TransactionItem("TRSPBL110625002", "PRD002", 2),
        TransactionItem("TRSPBL120625003", "PRD003", 2),
        TransactionItem("TRSPBL130625004", "PRD004", 2),
        TransactionItem("TRSPBL140625005", "PRD005", 2),

        TransactionItem("TRSTGH120625001", "PRD001", 2),
        TransactionItem("TRSTGH120625002", "PRD002", 2),
        TransactionItem("TRSTGH130625003", "PRD003", 2),
        TransactionItem("TRSTGH130625004", "PRD004", 2),
        TransactionItem("TRSTGH140625005", "PRD005", 2),
    )

    fun getTransactionItems(id : String) : List<TransactionItem> {
        return allData
    }

    fun getAllTransactionWithProduct(productId: String): List<Transaction> {
//        val txs = allData
//            .filter { it.productId == productId }
//            .mapNotNull { TransactionRepository.getTransactionById(it.transactionId) }
//        return txs

        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
        fun parseDate(dateStr: String): Date = TransactionRepository.sdf.parse(dateStr)!!
        val Kontak = DummyContactRepository.getAllContact()
        val balance = BalanceRepository.getAllBalance()
        return listOf(
            Transaction.Bill(parseDate("15-06-2025"), parseDate("26-06-2025"), Kontak[2], balance[2], "TRSTGH150625007", "Diproses", BigDecimal("450000")),
            Transaction.Sell(parseDate("16-06-2025"), Kontak[1], balance[3], "TRSPJL160625007", "Transfer", BigDecimal("900000")),
            Transaction.Purchase(parseDate("16-06-2025"), Kontak[1], balance[4], "TRSPBL160625007", "Proses", BigDecimal("2500000")),
        )

    }
}