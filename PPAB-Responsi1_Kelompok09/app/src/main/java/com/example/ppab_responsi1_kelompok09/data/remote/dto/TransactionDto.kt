package com.example.ppab_responsi1_kelompok09.data.remote.dto

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

data class TransactionDto (
    val id : String,
    val user_id : Int,
    val tanggal : String,
    val jenis : JenisTransaksi,
    val kontak_id : Int,
    val saldo_id : Int,
    val nominal : BigDecimal,
    val pembayaran : Pembayaran? = null,
    val status : StatusTransaksi? = null,
    val jatuh_tempo : String? = "",
    val dibayar : BigDecimal? = null,
    val items : List<TransactionItemDto>,
)

enum class JenisTransaksi {
    @SerializedName("penjualan") PENJUALAN,
    @SerializedName("pembelian") PEMBELIAN,
    @SerializedName("tagihan") TAGIHAN
}

enum class Pembayaran {
    @SerializedName("tunai") TUNAI,
    @SerializedName("bank transfer") BANK_TRANSFER,
    @SerializedName("kartu kredit") KARTU_KREDIT,
    @SerializedName("qris") QRIS,
    @SerializedName("lainnya") LAINNYA
}

enum class StatusTransaksi {
    @SerializedName("lunas") LUNAS,
    @SerializedName("diproses") DIPROSES,
    @SerializedName("jatuh tempo") JATUH_TEMPO
}