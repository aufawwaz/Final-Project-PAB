package com.example.ppab_responsi1_kelompok09.domain.repository

import com.example.ppab_responsi1_kelompok09.data.remote.retrofit.RetrofitInstance
import com.example.ppab_responsi1_kelompok09.domain.model.Product
import com.example.ppab_responsi1_kelompok09.data.mapper.toProduct


class ProductRepository {
    suspend fun getAllProducts(token: String): List<Product> {
        val response = RetrofitInstance.productApi.getAllProducts("Bearer $token")
        return response.data.map { it.toProduct() }
    }

    suspend fun getProductById(id: String, token: String): Product {
        val response = RetrofitInstance.productApi.getProductById("Bearer $token", id)
        return response.data.toProduct()
    }
}

//object ProductRepository {
//    private val product = listOf(
//        Product(
//            id = "PRD001",
//            productImage = R.drawable.img_product_1,
//            category = "MEN SHOES",
//            satuan = "Pcs",
//            productName = "Air Jordan 1 Mid",
//            productDescription = "Nikmati cita rasa istimewa dari Kopi Arabika Premium 250gr, pilihan terbaik bagi pecinta kopi sejati. Biji kopi ini dipetik langsung dari dataran tinggi Gayo, Aceh — terkenal akan kualitas Arabikanya yang mendunia. Proses panen dilakukan secara manual untuk memastikan hanya biji terbaik yang dipilih, lalu diproses dengan metode full-wash untuk menghasilkan karakter rasa yang bersih dan kompleks.\n" +
//                    "\n" +
//                    "Dengan aroma floral yang khas, acidity yang seimbang, serta aftertaste yang lembut dan tahan lama, kopi ini cocok diseduh menggunakan metode manual brew seperti V60, Chemex, atau French Press. Setiap cangkirnya menghadirkan pengalaman menikmati kopi yang autentik, bebas dari rasa pahit berlebih, dan tanpa tambahan bahan kimia.",
//            sold = 120,
//            stock = 30,
//            price = BigDecimal("1548000"),
//            modal = BigDecimal("1200000")
//        ),
//        Product(
//            id = "PRD002",
//            productImage = R.drawable.img_product_1,
//            category = "MEN SHOES",
//            satuan = "Liter",
//            productName = "Nike Air Max",
//            productDescription = "Sepatu lari dengan bantalan udara untuk kenyamanan maksimal.",
//            sold = 200,
//            stock = 50,
//            price = BigDecimal("1750000"),
//            modal = BigDecimal("1400000")
//        ),
//        Product(
//            id = "PRD003",
//            productImage = R.drawable.img_product_1,
//            category = "WOMEN SHOES",
//            satuan = "Pcs",
//            productName = "Adidas Ultraboost",
//            productDescription = "Sepatu lari ringan dengan teknologi Boost yang responsif.",
//            sold = 90,
//            stock = 20,
//            price = BigDecimal("1800000"),
//            modal = BigDecimal("1350000")
//        ),
//        Product(
//            id = "PRD004",
//            productImage = R.drawable.img_product_1,
//            category = "WOMEN SHOES",
//            satuan = "Pcs",
//            productName = "Nike Revolution",
//            productDescription = "Sepatu harian dengan gaya sporty dan harga terjangkau.",
//            sold = 150,
//            stock = 15,
//            price = BigDecimal("950000"),
//            modal = BigDecimal("700000")
//        ),
//        Product(
//            id = "PRD005",
//            productImage = R.drawable.img_product_1,
//            category = "KIDS SHOES",
//            satuan = "Pcs",
//            productName = "Converse Chuck Taylor",
//            productDescription = "Sneakers klasik untuk segala usia, termasuk anak-anak.",
//            sold = 75,
//            stock = 40,
//            price = BigDecimal("850000"),
//            modal = BigDecimal("600000")
//        ),
//        Product(
//            id = "PRD006",
//            productImage = R.drawable.img_product_1,
//            category = "KIDS SHOES",
//            satuan = "Pcs",
//            productName = "Vans Old Skool",
//            productDescription = "Sepatu skateboard populer dengan desain stripe ikonik.",
//            sold = 50,
//            stock = 25,
//            price = BigDecimal("990000"),
//            modal = BigDecimal("750000")
//        ),
//        Product(
//            id = "PRD007",
//            productImage = R.drawable.img_product_1,
//            category = "MEN SHOES",
//            satuan = "Pcs",
//            productName = "Puma RS-X",
//            productDescription = "Sneakers dengan gaya retro dan desain bulky modern.",
//            sold = 110,
//            stock = 18,
//            price = BigDecimal("1600000"),
//            modal = BigDecimal("1250000")
//        ),
//        Product(
//            id = "PRD008",
//            productImage = R.drawable.img_product_1,
//            category = "WOMEN SHOES",
//            satuan = "Pcs",
//            productName = "Reebok Classic",
//            productDescription = "Sepatu klasik dengan gaya vintage dan bahan kulit.",
//            sold = 85,
//            stock = 22,
//            price = BigDecimal("1025000"),
//            modal = BigDecimal("800000")
//        ),
//        Product(
//            id = "PRD009",
//            productImage = R.drawable.img_product_1,
//            category = "MEN SHOES",
//            satuan = "Pcs",
//            productName = "New Balance 574",
//            productDescription = "Sepatu retro dengan kenyamanan modern dan grip bagus.",
//            sold = 130,
//            stock = 28,
//            price = BigDecimal("1350000"),
//            modal = BigDecimal("1050000")
//        ),
//        Product(
//            id = "PRD010",
//            productImage = R.drawable.img_product_1,
//            category = "KIDS SHOES",
//            satuan = "Pcs",
//            productName = "Skechers Kids Flex",
//            productDescription = "Sepatu fleksibel dan ringan untuk aktivitas anak.",
//            sold = 60,
//            stock = 35,
//            price = BigDecimal("780000"),
//            modal = BigDecimal("550000")
//        )
//    )
//
//    fun getProductById(id: String): Product? {
//        return product.find { it.id == id }
//    }
//
//    fun getAllProducts(): List<Product> = product
//}