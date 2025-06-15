package com.example.ppab_responsi1_kelompok09.domain.repository

import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import java.math.BigDecimal
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object TransactionRepository {
    // Utility untuk parsing tanggal dari string
    private val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.getDefault())
    private fun parseDate(dateStr: String): Date = sdf.parse(dateStr)!!

    // Buat dapetin kontak
    private val Kontak = ContactRepository.getAllContact()

    // Buat dapetin balance
    private val balance = BalanceRepository.getAllBalance()

    // Object dummy
    private val transaction = listOf(
        Transaction.Sell(parseDate("11-06-2025"), Kontak[0], balance[0], "TRSPJL110625001", "Tunai", BigDecimal("500000")),
        Transaction.Sell(parseDate("12-06-2025"), Kontak[1], balance[1], "TRSPJL120625002", "QRIS", BigDecimal("1750000")),
        Transaction.Sell(parseDate("13-06-2025"), Kontak[2], balance[2], "TRSPJL130625003", "Kartu Kredit", BigDecimal("215000")),
        Transaction.Sell(parseDate("13-06-2025"), Kontak[0], balance[3], "TRSPJL130625004", "Transfer", BigDecimal("980000")),
        Transaction.Sell(parseDate("14-06-2025"), Kontak[1], balance[4], "TRSPJL140625005", "Tunai", BigDecimal("1500000")),

        Transaction.Purchase(parseDate("10-06-2025"), Kontak[0], balance[0], "TRSPBL100625001", "Diterima", BigDecimal("3200000")),
        Transaction.Purchase(parseDate("11-06-2025"), Kontak[1], balance[1], "TRSPBL110625002", "Proses", BigDecimal("1525000")),
        Transaction.Purchase(parseDate("12-06-2025"), Kontak[2], balance[2], "TRSPBL120625003", "Ditolak", BigDecimal("2140000")),
        Transaction.Purchase(parseDate("13-06-2025"), Kontak[0], balance[3], "TRSPBL130625004", "Diterima", BigDecimal("475000")),
        Transaction.Purchase(parseDate("14-06-2025"), Kontak[1], balance[4], "TRSPBL140625005", "Diterima", BigDecimal("980000")),

        Transaction.Bill(parseDate("12-06-2025"), parseDate("20-06-2025"), Kontak[0], balance[0], "TRSTGH120625001", "Lunas", BigDecimal("630000")),
        Transaction.Bill(parseDate("12-06-2025"), parseDate("18-06-2025"), Kontak[1], balance[1], "TRSTGH120625002", "Diproses", BigDecimal("820000")),
        Transaction.Bill(parseDate("13-06-2025"), parseDate("21-06-2025"), Kontak[2], balance[2], "TRSTGH130625003", "Jatuh Tempo", BigDecimal("175000")),
        Transaction.Bill(parseDate("13-06-2025"), parseDate("22-06-2025"), Kontak[0], balance[3], "TRSTGH130625004", "Diproses", BigDecimal("530000")),
        Transaction.Bill(parseDate("14-06-2025"), parseDate("24-06-2025"), Kontak[1], balance[4], "TRSTGH140625005", "Lunas", BigDecimal("920000")),

        Transaction.Sell(parseDate("15-06-2025"), Kontak[2], balance[0], "TRSPJL150625006", "QRIS", BigDecimal("775000")),
        Transaction.Purchase(parseDate("15-06-2025"), Kontak[2], balance[1], "TRSPBL150625006", "Diterima", BigDecimal("1230000")),
        Transaction.Bill(parseDate("15-06-2025"), parseDate("26-06-2025"), Kontak[2], balance[2], "TRSTGH150625007", "Diproses", BigDecimal("450000")),
        Transaction.Sell(parseDate("16-06-2025"), Kontak[1], balance[3], "TRSPJL160625007", "Transfer", BigDecimal("900000")),
        Transaction.Purchase(parseDate("16-06-2025"), Kontak[1], balance[4], "TRSPBL160625007", "Proses", BigDecimal("2500000")),

        Transaction.Sell(parseDate("10-05-2025"), Kontak[0], balance[0], "TRSPJL100525001", "Tunai", BigDecimal("100000")),
        Transaction.Sell(parseDate("15-05-2025"), Kontak[1], balance[1], "TRSPJL150525002", "QRIS", BigDecimal("200000")),
    )

    fun getTransactionById(id : String): Transaction? {
        return transaction.find {
            when (it) {
                is Transaction.Sell -> it.id == id
                is Transaction.Purchase -> it.id == id
                is Transaction.Bill -> it.id == id
            }
        }
    }

    fun getAllTransaction(): List<Transaction> = transaction
}