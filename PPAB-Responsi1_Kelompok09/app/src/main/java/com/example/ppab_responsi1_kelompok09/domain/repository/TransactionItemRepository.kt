package com.example.ppab_responsi1_kelompok09.domain.repository

import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.data.mapper.toDomain
import com.example.ppab_responsi1_kelompok09.data.remote.RetrofitInstance
import com.example.ppab_responsi1_kelompok09.domain.model.Product
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.model.TransactionItem
import java.text.SimpleDateFormat
import java.math.BigDecimal
import java.util.Date
import java.util.Locale

object TransactionItemRepository {
    suspend fun getAllTransactionItems(token: String): List<TransactionItem> {
        val response = RetrofitInstance.transactionApi.getTransactions("Bearer $token")
        return allDummyData
    }

    val dummyProduct = Product(
            id = "PRD001",
            productImage = "",
            category = "MEN SHOES",
            satuan = "Pcs",
            productName = "Air Jordan 1 Mid",
            productDescription = "Nikmati cita rasa istimewa dari Kopi Arabika Premium 250gr, pilihan terbaik bagi pecinta kopi sejati. Biji kopi ini dipetik langsung dari dataran tinggi Gayo, Aceh — terkenal akan kualitas Arabikanya yang mendunia. Proses panen dilakukan secara manual untuk memastikan hanya biji terbaik yang dipilih, lalu diproses dengan metode full-wash untuk menghasilkan karakter rasa yang bersih dan kompleks.\n" +
                    "\n" +
                    "Dengan aroma floral yang khas, acidity yang seimbang, serta aftertaste yang lembut dan tahan lama, kopi ini cocok diseduh menggunakan metode manual brew seperti V60, Chemex, atau French Press. Setiap cangkirnya menghadirkan pengalaman menikmati kopi yang autentik, bebas dari rasa pahit berlebih, dan tanpa tambahan bahan kimia.",
            sold = 120,
            stock = 30,
            price = BigDecimal("1548000"),
            modal = BigDecimal("1200000")
        )

     val allDummyData = listOf(
//        DUMMY DATAS
        TransactionItem("TRSPJL110625001", "PRD001", dummyProduct, 2),
        TransactionItem("TRSPJL120625002", "PRD002",  dummyProduct, 2),
        TransactionItem("TRSPJL130625003", "PRD003", dummyProduct, 2),
        TransactionItem("TRSPJL130625004", "PRD004", dummyProduct, 2),
        TransactionItem("TRSPJL140625005", "PRD005", dummyProduct, 2),

        TransactionItem("TRSPBL100625001", "PRD001", dummyProduct, 2),
        TransactionItem("TRSPBL110625002", "PRD002", dummyProduct, 2),
        TransactionItem("TRSPBL120625003", "PRD003", dummyProduct, 2),
        TransactionItem("TRSPBL130625004", "PRD004", dummyProduct, 2),
        TransactionItem("TRSPBL140625005", "PRD005", dummyProduct, 2),

        TransactionItem("TRSTGH120625001", "PRD001", dummyProduct, 2),
        TransactionItem("TRSTGH120625002", "PRD002", dummyProduct, 2),
        TransactionItem("TRSTGH130625003", "PRD003", dummyProduct, 2),
        TransactionItem("TRSTGH130625004", "PRD004", dummyProduct, 2),
        TransactionItem("TRSTGH140625005", "PRD005", dummyProduct, 2),
    )

    fun getTransactionItems(id : String) : List<TransactionItem> {
        return allDummyData
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
            Transaction.Bill(parseDate("15-06-2025"), parseDate("26-06-2025"), Kontak[2], balance[2], "TRSTGH150625007", "Diproses", BigDecimal("450000"), allDummyData),
            Transaction.Sell(parseDate("16-06-2025"), Kontak[1], balance[3], "TRSPJL160625007", "Transfer", BigDecimal("900000"), allDummyData),
            Transaction.Purchase(parseDate("16-06-2025"), Kontak[1], balance[4], "TRSPBL160625007", "Proses", BigDecimal("2500000"), allDummyData),
        )

    }
}