package com.example.ppab_responsi1_kelompok09.common.component

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.common.style.AppText
import com.example.ppab_responsi1_kelompok09.common.style.dropShadow200
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Success
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Composable
fun ProductCard (
    onCLick : () -> Unit = {},
    productImage : Int,
    category : String,
    productName : String,
    sold : String,
    stock : String,
    price : String
) {
    var satuan by remember { mutableStateOf("/Pcs") }

    Column (
        modifier = Modifier
            .dropShadow200(16.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(White)
            .height(248.dp)
            .width(148.dp)
            .clickable{}
    ) {
        Image(
            painter = painterResource(productImage),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .height(140.dp)
                .dropShadow200(16.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
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
                            Icon(
                                painter = painterResource(R.drawable.home_fill),
                                contentDescription = null,
                                modifier = Modifier.width(12.dp),
                                tint = Gray
                            )
                            AppText(
                                text = sold,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                color = Gray
                            )
                        }
                        Row (
                            horizontalArrangement = Arrangement.spacedBy(4.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Icon(
                                painter = painterResource(R.drawable.home_fill),
                                contentDescription = null,
                                modifier = Modifier.width(12.dp),
                                tint = Gray
                            )
                            AppText(
                                text = stock,
                                fontSize = 10.sp,
                                fontWeight = FontWeight.Normal,
                                color = Gray
                            )
                        }
                    }
                }
                AppText(
                    text = price + satuan,
                    fontWeight = FontWeight.Medium,
                    fontSize = 12.sp,
                    color = Success
                )
            }
        }
    }
}