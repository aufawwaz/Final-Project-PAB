package com.example.ppab_responsi1_kelompok09.domain.repository

import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.model.TransactionItem

object TransactionItemRepository {
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

    fun getTransactionItems(id: String): List<TransactionItem> {
        return allData.filter { it.transactionId == id }
    }

    fun getAllTransactionWithProduct(productId: String): List<Transaction> {
        val txs = allData
            .filter { it.productId == productId }
            .mapNotNull { TransactionRepository.getTransactionById(it.transactionId) }
        return txs
    }
}