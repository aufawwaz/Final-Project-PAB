package com.example.ppab_responsi1_kelompok09.presentation.transaction

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.presentation.components.BottomSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomButton
import com.example.ppab_responsi1_kelompok09.presentation.components.PageHeader
import com.example.ppab_responsi1_kelompok09.presentation.components.SearchBarFilter
import com.example.ppab_responsi1_kelompok09.presentation.components.TonalIcon
import com.example.ppab_responsi1_kelompok09.presentation.components.TransactionCard
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.dropShadow200
import com.example.ppab_responsi1_kelompok09.domain.model.Transaction
import com.example.ppab_responsi1_kelompok09.domain.model.transactionList
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.White

@Preview
@Composable
fun TransactionScreen(navController: NavController = rememberNavController()) {
    Box (
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            PageHeader(
                pagetitle = "Transaksi",
                title = "Total Transaksi",
                iconRes = R.drawable.ic_transaksi_fill,
                description = "10 Transaksi"
            )

            Column(
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                KategoriTransaksi()
                AppText(
                    text = "Transaksi Hari ini",
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
                SearchBarFilter("Cari Transaksi")
                PesananTerbaruList()
                BottomSpacer()
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = -(12.dp), y = -(138.dp))
                .width(200.dp)
        ) {
            CustomButton({ }, {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        painter = painterResource(R.drawable.ic_transaksi_fill),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    AppText("Tambah Transaksi", 12.sp, color = White)
                }
            })
        }
    }
}

@Composable
private fun KategoriTransaksi () {
    Row (
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        KategoriTransaksiItem(
            iconRes = R.drawable.ic_penjualan_fill,
            category = "Penjualan",
            modifier = Modifier.weight(1f)
        )
        KategoriTransaksiItem(
            iconRes = R.drawable.ic_pembelian_fill,
            category = "Pembelian",
            modifier = Modifier.weight(1f)
        )
        KategoriTransaksiItem(
            iconRes = R.drawable.ic_tagihan_fill,
            category = "Tagihan",
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun KategoriTransaksiItem (
    iconRes : Int,
    category : String,
    modifier : Modifier = Modifier,
    onClick : () -> Unit = {}
) {
    Row (
        modifier = modifier
            .height(44.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(Primary.copy(0.1f))
            .clickable{ onClick },
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            painter = painterResource(iconRes),
            contentDescription = null,
            tint = Primary,
            modifier = Modifier.height(14.dp)
        )
        Spacer(Modifier.width(4.dp))
        AppText(
            text = category,
            fontWeight = FontWeight.SemiBold,
            fontSize = 12.sp,
            color = Primary
        )
    }
}

@Composable
private fun PesananTerbaruList() {
    LazyColumn (
        modifier = Modifier
            .padding(horizontal = 16.dp)
            .height((transactionList.size * 113).dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(transactionList) { transactionItem ->
            val iconRes = when (transactionItem) {
                is Transaction.Sell -> R.drawable.ic_penjualan_fill
                is Transaction.Purchase -> R.drawable.ic_pembelian_fill
                is Transaction.Bill -> R.drawable.ic_tagihan_fill
            }
            val currentId = when (transactionItem) {
                is Transaction.Sell -> transactionItem.id
                is Transaction.Purchase -> transactionItem.id
                is Transaction.Bill -> transactionItem.id
            }
            Column (
                modifier = Modifier
                    .dropShadow200(8.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .background(White)
                    .clickable { }
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AppText(
                    text = currentId,
                    fontWeight = FontWeight.Normal,
                    fontSize = 12.sp
                )
                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Gray.copy(0.3f))
                        .height(0.5.dp)
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    TonalIcon(
                        iconRes = iconRes,
                        iconHeight = 24.dp,
                        boxSize = 44.dp
                    )
                    TransactionCard(
                        transaction = transactionItem,
                        isIdInCard = false
                    )
                }
            }
        }
    }
}