    package com.example.ppab_responsi1_kelompok09.data.mapper

    import com.example.ppab_responsi1_kelompok09.data.remote.dto.JenisTransaksi
    import com.example.ppab_responsi1_kelompok09.data.remote.dto.Pembayaran
    import com.example.ppab_responsi1_kelompok09.data.remote.dto.StatusTransaksi
    import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionDto
    import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
    import com.example.ppab_responsi1_kelompok09.domain.repository.BalanceRepository
    import com.example.ppab_responsi1_kelompok09.domain.repository.ContactRepository
    import java.text.SimpleDateFormat
    import java.util.Date
    import java.util.Locale

    fun TransactionDto.toDomain(): Transaction {
    //    DUMMY DATA DULU
        val Kontak = ContactRepository.getAllContact()
        val Balance = BalanceRepository.getAllBalance()
        val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        val parsedTanggal = formatter.parse(tanggal) ?: Date()
        val parsedJatuhTempo = jatuh_tempo?.let { formatter.parse(it) } ?: parsedTanggal

        when(jenis){
            JenisTransaksi.PENJUALAN -> return Transaction.Sell(
                id = id,
                date = parsedTanggal,
                customer = Kontak[0],
                total = nominal,
                paymentMethod = when(pembayaran) {
                    Pembayaran.TUNAI -> "Tunai"
                    Pembayaran.BANK_TRANSFER -> "Bank Transfer"
                    Pembayaran.KARTU_KREDIT -> "Kartu Kredit"
                    Pembayaran.QRIS -> "QRIS"
                    Pembayaran.LAINNYA -> "Lainnya"
                    null -> "Lainnya"
                },
                balance = Balance[0],
            )
            JenisTransaksi.PEMBELIAN -> return Transaction.Purchase(
                id = id,
                date = parsedTanggal,
                supplier = Kontak[0],
                total = nominal,
                status = when(status) {
                    StatusTransaksi.LUNAS -> "Lunas"
                    StatusTransaksi.DIPROSES -> "Diproses"
                    StatusTransaksi.JATUH_TEMPO -> "Jatuh Tempo"
                    null -> "Lunas"
                },
                balance = Balance[0],
            )
            JenisTransaksi.TAGIHAN -> return Transaction.Bill(
                id = id,
                date = parsedTanggal,
                outdate = parsedJatuhTempo,
                customer = Kontak[0],
                total = nominal,
                status = when(status) {
                    StatusTransaksi.LUNAS -> "Lunas"
                    StatusTransaksi.DIPROSES -> "Diproses"
                    StatusTransaksi.JATUH_TEMPO -> "Jatuh Tempo"
                    null -> "Lunas"
                },
                balance = Balance[0],
            )
        }
    }