package com.example.ppab_responsi1_kelompok09.domain.repository

import com.example.ppab_responsi1_kelompok09.domain.model.Balance
import java.math.BigDecimal

object BalanceRepository {
    private val balance = listOf(
        Balance("BLNC001", "Cash", "Dompet Kas", BigDecimal("10000000")),
        Balance("BLNC002", "Bank", "Rekening BCA", BigDecimal("25000000")),
        Balance("BLNC003", "Bank", "Rekening BRI", BigDecimal("17500000")),
        Balance("BLNC004", "Cash", "DANA", BigDecimal("5000000")),
        Balance("BLNC004", "Cash", "DANA", BigDecimal("5000000")),
        Balance("BLNC004", "Cash", "DANA", BigDecimal("5000000")),
        Balance("BLNC004", "Cash", "DANA", BigDecimal("5000000")),
        Balance("BLNC004", "Cash", "DANA", BigDecimal("5000000")),
        Balance("BLNC004", "Cash", "DANA", BigDecimal("5000000")),
        Balance("BLNC005", "Bank", "Rekening Danamon", BigDecimal("3200000"))
    )

    fun getBalanceById(id : String): Balance? {
        return balance.find { it.id == id }
    }

    fun getAllBalance(): List<Balance> = balance
}