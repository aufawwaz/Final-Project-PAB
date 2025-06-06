package com.example.ppab_responsi1_kelompok09.presentation.contact

import androidx.compose.foundation.Image
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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomButton
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomSwitch
import com.example.ppab_responsi1_kelompok09.presentation.components.HorizontalLine
import com.example.ppab_responsi1_kelompok09.presentation.components.InputTextForm
import com.example.ppab_responsi1_kelompok09.presentation.components.PageHeader
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.domain.model.Contact
import com.example.ppab_responsi1_kelompok09.domain.model.KontakPelanggan
import com.example.ppab_responsi1_kelompok09.domain.model.KontakPenjual
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.White

//@Preview (showBackground = true)
@Composable
fun ContactScreen(navController: NavController = rememberNavController()) {
    Box(
        modifier = Modifier.fillMaxSize()
    ){
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            PageHeader(
                "Kontak",
                "Total Kontak",
                iconRes = R.drawable.ic_pelanggan_fill,
                "5 Kontak")

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ){
                var contactSearchValue by rememberSaveable { mutableStateOf("") }
                InputTextForm(
                    contactSearchValue,
                    { contactSearchValue = it },
                    "Search Contact",
                    R.drawable.ic_search
                )

                var isChecked by remember { mutableStateOf(false) }
                CustomSwitch(
                    checked = isChecked,
                    onCheckedChange = { isChecked = it },
                    text1 = "Pelanggan",
                    text2 = "Penjual"
                )

                if(!isChecked) ShowContact(KontakPelanggan)
                else ShowContact(KontakPenjual)
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .offset(x = -(12.dp), y = -(138.dp))
                .width(180.dp)
        ){
            CustomButton({ }, {
                Row (
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Icon(
                        painter = painterResource(R.drawable.ic_add_pelanggan),
                        contentDescription = null,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(Modifier.width(8.dp))
                    AppText("Tambah Kontak", 12.sp, color = White)
                }
            })
        }
    }
}

@Composable
private fun ShowContact(list: List<Contact>) {
    LazyColumn(
        modifier = Modifier
            .fillMaxWidth()
            .height((list.size * 64).dp),
        userScrollEnabled = false
    ) {
        items(list) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable{ }
                    .padding(8.dp)
            ) {
                Image(
                    painter = painterResource(item.imageRes),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                )
                Column(
                    modifier = Modifier.padding(start = 8.dp)
                ) {
                    AppText(item.name, 14.sp)
                    AppText(item.number, 12.sp, color = Gray)
                }
            }
            HorizontalLine(1f, color = Gray.copy(0.1f))
        }
    }
    Spacer(Modifier.height(96.dp))
}