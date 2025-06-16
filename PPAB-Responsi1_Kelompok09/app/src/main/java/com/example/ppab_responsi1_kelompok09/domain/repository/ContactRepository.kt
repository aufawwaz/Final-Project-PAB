package com.example.ppab_responsi1_kelompok09.domain.repository

import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.Contact
import com.example.ppab_responsi1_kelompok09.domain.model.Product

object ContactRepository {
    private val contact = listOf(
        Contact(
            id = "C001",
            nama_kontak = "Aril Fadla Hudallah",
            nomor_kontak = "081234567890",
            image_kontak = R.drawable.img_profile_picture,
            email_kontak = "aril@example.com",
            alamat_kontak = "Jl. Melati No. 10, Surakarta",
            jumlah_transaksi = 15
        ),
        Contact(
            id = "C002",
            nama_kontak = "Ariel Josua",
            nomor_kontak = "081298765432",
            image_kontak = R.drawable.img_profile_picture,
            email_kontak = "ariel.j@example.com",
            alamat_kontak = "Jl. Kenanga No. 5, Semarang",
            jumlah_transaksi = 12
        ),
        Contact(
            id = "C003",
            nama_kontak = "Aufa Fawwaz Aryasatya",
            nomor_kontak = "089901234567",
            image_kontak = R.drawable.img_profile_picture,
            email_kontak = "aufa.fawwaz@example.com",
            alamat_kontak = "Jl. Anggrek No. 22, Yogyakarta",
            jumlah_transaksi = 9
        ),
        Contact(
            id = "C004",
            nama_kontak = "Salsa Kamila",
            nomor_kontak = "082134567890",
            image_kontak = R.drawable.img_profile_picture,
            email_kontak = "salsa.k@example.com",
            alamat_kontak = "Jl. Cempaka No. 9, Surabaya",
            jumlah_transaksi = 18
        ),
        Contact(
            id = "C005",
            nama_kontak = "Ilham Saputra",
            nomor_kontak = "085612345678",
            image_kontak = R.drawable.img_profile_picture,
            email_kontak = "ilham.s@example.com",
            alamat_kontak = "Jl. Mawar No. 13, Bandung",
            jumlah_transaksi = 7
        ),
        Contact(
            id = "C006",
            nama_kontak = "Putri Ayu",
            nomor_kontak = "087812345690",
            image_kontak = R.drawable.img_profile_picture,
            email_kontak = "putri.a@example.com",
            alamat_kontak = "Jl. Flamboyan No. 33, Jakarta",
            jumlah_transaksi = 22
        ),
        Contact(
            id = "C007",
            nama_kontak = "Rizky Maulana",
            nomor_kontak = "083812345677",
            image_kontak = R.drawable.img_profile_picture,
            email_kontak = "rizky.m@example.com",
            alamat_kontak = "Jl. Seruni No. 18, Malang",
            jumlah_transaksi = 10
        ),
        Contact(
            id = "C008",
            nama_kontak = "Dina Puspita",
            nomor_kontak = "081999888777",
            image_kontak = R.drawable.img_profile_picture,
            email_kontak = "dina.p@example.com",
            alamat_kontak = "Jl. Dahlia No. 4, Makassar",
            jumlah_transaksi = 5
        ),
        Contact(
            id = "C009",
            nama_kontak = "Fadli Nugraha",
            nomor_kontak = "089812345678",
            image_kontak = R.drawable.img_profile_picture,
            email_kontak = "fadli.n@example.com",
            alamat_kontak = "Jl. Bougenville No. 14, Medan",
            jumlah_transaksi = 14
        ),
        Contact(
            id = "C010",
            nama_kontak = "Nadya Saraswati",
            nomor_kontak = "082233445566",
            image_kontak = R.drawable.img_profile_picture,
            email_kontak = "nadya.s@example.com",
            alamat_kontak = "Jl. Teratai No. 7, Denpasar",
            jumlah_transaksi = 19
        )
    )


    fun getContactById(id: String): Contact? {
        return contact.find { it.id == id }
    }

    fun getAllContact(): List<Contact> = contact
}