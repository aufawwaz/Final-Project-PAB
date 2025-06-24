package com.example.ppab_responsi1_kelompok09.presentation.components

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Success
import com.example.ppab_responsi1_kelompok09.ui.theme.Warning
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import java.math.BigDecimal
import java.text.NumberFormat
import java.util.Locale
import androidx.compose.ui.platform.LocalContext
import coil.compose.AsyncImage
import coil.request.CachePolicy

fun getStockColor(stock: Long) : Color {
    return when {
        stock.toInt() == 0 -> Danger
        stock.toInt() <= 10 -> Warning
        else -> Gray
    }
}

fun formatToCurrency(value: Number): String {
    val amount = when (value) {
        is BigDecimal -> value
        is Long -> BigDecimal.valueOf(value)
        is Int -> BigDecimal.valueOf(value.toLong())
        is Double -> BigDecimal.valueOf(value.toLong())
        else -> BigDecimal.ZERO
    }

    val formatter = NumberFormat.getCurrencyInstance(Locale("in", "ID")).apply {
        maximumFractionDigits = 0
        minimumFractionDigits = 0
    }

    return formatter.format(amount)
}

@Composable
fun ProductCard (
    onCLick : () -> Unit = {},
    productImage : String,
    category : String,
    productName : String,
    sold : Int,
    stock : Long,
    price : BigDecimal,
    modifier : Modifier
) {
    var satuan by remember { mutableStateOf("/Pcs") }
    val context = LocalContext.current

    Column (
        modifier = modifier
            .dropShadow200(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .height(248.dp)
            .width(148.dp)
            .clickable{ onCLick() }
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(productImage)
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
            modifier = Modifier
                .padding(vertical = 8.dp, horizontal = 12.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(4.dp)
        ) {
            AppText(
                text = category,
                fontSize = 10.sp,
                fontWeight = FontWeight.Normal
            )
            Column (
                modifier = Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Column (
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    AppText(
                        text = productName,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Row (
                        horizontalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon (
                                painter = painterResource(R.drawable.ic_terjual),
                                contentDescription = null,
                                tint = Gray,
                                modifier = Modifier.height(10.dp)
                            )
                            AppText(
                                text = sold.toString(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                color = Gray
                            )
                        }
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon (
                                painter = painterResource(R.drawable.ic_stok),
                                contentDescription = null,
                                tint = getStockColor(stock),
                                modifier = Modifier.height(10.dp)
                            )
                            AppText(
                                text = stock.toString(),
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                color = getStockColor(stock)
                            )
                        }
                    }
                }
                AppText(
                    text = formatToCurrency(price) + satuan,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Success
                )
            }
        }
    }
}