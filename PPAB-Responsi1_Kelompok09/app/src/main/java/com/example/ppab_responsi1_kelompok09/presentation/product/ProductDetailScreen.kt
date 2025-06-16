package com.example.ppab_responsi1_kelompok09.presentation.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.model.Product
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.model.TransactionItem
import com.example.ppab_responsi1_kelompok09.domain.repository.ProductRepository
import com.example.ppab_responsi1_kelompok09.domain.repository.TransactionItemRepository
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomSwitch
import com.example.ppab_responsi1_kelompok09.presentation.components.HorizontalLine
import com.example.ppab_responsi1_kelompok09.presentation.components.TopSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.presentation.components.formatToCurrency
import com.example.ppab_responsi1_kelompok09.presentation.components.shadow
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Success
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import java.time.ZoneId
import androidx.lifecycle.viewmodel.compose.viewModel
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ProductDetailScreen(
    navController: NavController,
    productId: String,
    token: String,
    viewModel: ProductViewModel = viewModel()
) {
    val state by viewModel.state.collectAsState()
    val product = state.product

    LaunchedEffect(productId) {
        viewModel.fetchProductDetail(productId, token)
    }

    if (state.isLoading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    } else if (product == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Produk tidak ditemukan atau gagal dimuat.")
        }
    } else {
        Box(modifier = Modifier.fillMaxSize()) {
            Column (
                verticalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {
                ImageBox(navController, product)

                Row (
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    AppText(
                        text = product.productName,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp
                    )
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(24.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Dark.copy(0.09f))
                            .padding(horizontal = 8.dp)
                    ) {
                        AppText(
                            text = product.category,
                            fontWeight = FontWeight.Light,
                            fontSize = 10.sp
                        )
                    }
                }

                var isChecked by rememberSaveable { mutableStateOf(false) }
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                ) {
                    CustomSwitch(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        text1 = "Detail",
                        text2 = "Aktivitas"
                    )
                }

                if (!isChecked) ProductDescription(product)
                else ProductActivity(product, navController)
            }
        }
    }
}


@Composable
private fun ImageBox (
    navController: NavController,
    product: Product
) {
    Box (
        modifier = Modifier.fillMaxWidth()
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.productImage)
                .diskCachePolicy(CachePolicy.DISABLED) // hindari cache error
                .build(),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            placeholder = painterResource(id = R.drawable.img_profile_picture),
            error = painterResource(id = R.drawable.img_profile_picture),
            modifier = Modifier
                .height(150.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(8.dp))
        )
        Column (
            verticalArrangement = Arrangement.spacedBy(32.dp),
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.TopCenter)
        ) {
            TopSpacer()
            Row (
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .padding(horizontal = 16.dp)
            ) {
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .dropShadow200(999.dp)
                        .background(White)
                        .clickable{ navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter =  painterResource(R.drawable.ic_previous),
                        contentDescription = null,
                        tint = Dark,
                        modifier = Modifier.size(24.dp)
                    )
                }
                Box(
                    modifier = Modifier
                        .size(44.dp)
                        .clip(CircleShape)
                        .dropShadow200(999.dp)
                        .background(White)
                        .clickable{ navController.popBackStack() },
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter =  painterResource(R.drawable.ic_titik_tiga),
                        contentDescription = null,
                        tint = Dark,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun ProductDescription(
    product: Product
) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Box (
            modifier = Modifier
                .fillMaxWidth()
                .height(58.dp)
                .verticalScroll(rememberScrollState())
        ) {
            AppText(
                text = product.productDescription,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal,
                lineHeight = 14.sp
            )
        }
        AppText(
            text = formatToCurrency(product.price) + "/" + product.satuan,
            color = Success,
            fontWeight = FontWeight.Bold,
            fontSize = 16.sp
        )
        Box(Modifier.fillMaxWidth().height(1.dp).background(Dark.copy(0.2f)))
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText(
                text = "Stok Tersedia",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            AppText(
                text = product.stock.toString(),
                fontWeight = FontWeight.SemiBold
            )
        }
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText(
                text = "Terjual",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            AppText(
                text = product.sold.toString() + " " + product.satuan,
                fontWeight = FontWeight.SemiBold
            )
        }
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText(
                text = "Harga Modal",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            AppText(
                text = formatToCurrency(product.modal),
                fontWeight = FontWeight.SemiBold
            )
        }
        Row (
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            AppText(
                text = "Keuntungan",
                fontWeight = FontWeight.Normal,
                fontSize = 12.sp
            )
            val profit = product.price - product.modal
            AppText(
                text = formatToCurrency(profit) + "/Penjualan",
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
private fun ProductActivity(
    product: Product,
    navController: NavController
) {
    val transactions = TransactionItemRepository.getAllTransactionWithProduct(product.id)

    Column (
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        transactions.forEach{
            when(it){
                is Transaction.Sell -> ProductActivityCardSell(it, product, navController)
                is Transaction.Purchase -> ProductActivityCardPurchase(it, product, navController)
                is Transaction.Bill -> ProductActivityCardBill(it, product, navController)
            }
        }
        Spacer(Modifier.height(30.dp))
    }
}

@Composable
private fun ProductActivityCardSell(transaction: Transaction.Sell, product: Product, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(Color.Black.copy(0.1f), 16.dp, 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .clickable{navController.navigate("penjualan_detail/" + transaction.id )}
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID"))
        val date = transaction.date
        val localDate = date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val formattedDate = dateFormatter.format(localDate)

        val transactionItems = TransactionItemRepository.getTransactionItems(transaction.id)
        val thisProductTransaction = transactionItems.find{ it.productId == product.id }

        AppText("Penjualan", 16.sp, FontWeight.SemiBold)
        HorizontalLine(1f, color = Gray.copy(0.5f))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                AppText(transaction.customer.nama_kontak)
                AppText(formattedDate, 10.sp, color = Gray)
            }
            AppText(
                "- " + thisProductTransaction?.amount + " " + product.satuan,
                16.sp,
                FontWeight.SemiBold,
                Danger
            )
        }
    }
}

@Composable
private fun ProductActivityCardPurchase(transaction: Transaction.Purchase, product: Product, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(Color.Black.copy(0.1f), 16.dp, 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .clickable{navController.navigate("pembelian_detail/" + transaction.id )}
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID"))
        val date = transaction.date
        val localDate = date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val formattedDate = dateFormatter.format(localDate)

        val transactionItems = TransactionItemRepository.getTransactionItems(transaction.id)
        val thisProductTransaction = transactionItems.find{ it.productId == product.id }

        AppText("Pembelian", 16.sp, FontWeight.SemiBold)
        HorizontalLine(1f, color = Gray.copy(0.5f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                AppText(transaction.supplier.nama_kontak)
                AppText(formattedDate, 10.sp, color = Gray)
            }
            AppText(
                "- " + thisProductTransaction?.amount + " " + product.satuan,
                16.sp,
                FontWeight.SemiBold,
                Danger
            )
        }
    }
}

@Composable
private fun ProductActivityCardBill(transaction: Transaction.Bill, product: Product, navController: NavController){
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(Color.Black.copy(0.1f), 16.dp, 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .clickable{navController.navigate("tagihan_detail/" + transaction.id )}
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        val dateFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID"))
        val date = transaction.date
        val localDate = date.toInstant()
            .atZone(ZoneId.systemDefault())
            .toLocalDate()
        val formattedDate = dateFormatter.format(localDate)

        val transactionItems = TransactionItemRepository.getTransactionItems(transaction.id)
        val thisProductTransaction = transactionItems.find{ it.productId == product.id }

        AppText("Tagihan", 16.sp, FontWeight.SemiBold)
        HorizontalLine(1f, color =  Gray.copy(0.5f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column {
                AppText(transaction.customer.nama_kontak)
                AppText(formattedDate, 10.sp, color = Gray)
            }
            AppText(
                "- " + thisProductTransaction?.amount + " " + product.satuan  ,
                16.sp,
                FontWeight.SemiBold,
                Success
            )
        }
    }
}