package com.example.ppab_responsi1_kelompok09.presentation.contact

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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.runtime.saveable.rememberSaveable
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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.data.remote.RetrofitInstance
import com.example.ppab_responsi1_kelompok09.data.remote.dto.TransactionContact
import com.example.ppab_responsi1_kelompok09.data.repository.ContactRepositoryImpl
import com.example.ppab_responsi1_kelompok09.domain.model.Contact
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetContactDetailUseCase
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.CustomSwitch
import com.example.ppab_responsi1_kelompok09.presentation.components.HeaderPageOnBack
import com.example.ppab_responsi1_kelompok09.presentation.components.HorizontalLine
import com.example.ppab_responsi1_kelompok09.presentation.components.shadow
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Dark
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary
import com.example.ppab_responsi1_kelompok09.ui.theme.Primary100
import com.example.ppab_responsi1_kelompok09.ui.theme.Success
import com.example.ppab_responsi1_kelompok09.ui.theme.Warning
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.format.DateTimeFormatter
import java.util.Locale

@Composable
fun ContactDetailScreen(
    navController: NavController,
    contactId: Int,
    token: String
) {
    val viewModel: ContactDetailViewModel = viewModel(
        factory = ContactDetailViewModelFactory(
            getContactDetailUseCase = GetContactDetailUseCase(
                repository = ContactRepositoryImpl(RetrofitInstance.contactApi)
            ),
            token = token
        )
    )

    val contactDetailState by viewModel.contactDetail.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()

    LaunchedEffect(contactId) {
        viewModel.loadContactDetail(contactId)
    }

    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    if (error != null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Terjadi kesalahan: $error")
        }
        return
    }

    val contact = contactDetailState?.contact
    val transactions = contactDetailState?.transactions ?: emptyList()

    if (contact == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Data tidak ditemukan")
        }
    } else {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(White)
                .padding(vertical = 20.dp),
        ) {
            Column {
                HeaderPageOnBack(
                    onClick = { navController.popBackStack() },
                    text = "Detail Kontak"
                )

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .verticalScroll(rememberScrollState())
                ) {
                    Spacer(Modifier.height(1.dp))
                    ImageNameSection(contact)

                    var isChecked by rememberSaveable { mutableStateOf(false) }
                    CustomSwitch(
                        checked = isChecked,
                        onCheckedChange = { isChecked = it },
                        text1 = "Detail",
                        text2 = "Transaksi"
                    )

                    Spacer(Modifier.height(0.dp))

                    if (!isChecked) ContactDescription(contact)
                    else ContactActivity(transactions, navController)
                }
                Spacer(Modifier.height(40.dp))
            }
        }
    }
}



@Composable
private fun ImageNameSection(contact: Contact) {
    Column (
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier.fillMaxWidth()
    ) {
        if (contact.image_kontak != null) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(contact.getImageUrl())
                    .diskCachePolicy(CachePolicy.DISABLED)
                    .build(),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                placeholder = painterResource(id = R.drawable.img_profile_picture),
                error = painterResource(id = R.drawable.img_profile_picture),
                modifier = Modifier
                    .clip(CircleShape)
                    .size(120.dp)
            )
        }  else {
            Box(
                modifier = Modifier
                    .size(120.dp)
                    .clip(CircleShape)
                    .background(Color(0XFFEDEDEF)),
                contentAlignment = Alignment.Center
            ) {
                AppText(
                    text = contact.nama_kontak.substring(0, 1).uppercase(),
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            }
        }
        AppText(
            text = contact.nama_kontak,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Box(Modifier.fillMaxWidth().height(1.dp).background(Dark.copy(0.2f)))
    }
}

@Composable
private fun ContactDescription(contact: Contact) {
    data class ContactDescriptionItem(val icon: Int, val text: String)

    val descriptionList = listOfNotNull(
        contact.nomor_handphone?.let { ContactDescriptionItem(R.drawable.ic_phone, it) },
        contact.email_kontak?.let { ContactDescriptionItem(R.drawable.ic_email, it) },
        contact.alamat_kontak?.let { ContactDescriptionItem(R.drawable.ic_location, it) }
    )

    LazyColumn(
        modifier = Modifier.height(200.dp),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        items(descriptionList.size) { index ->
            val item = descriptionList[index]
            Row(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Icon(
                    painter = painterResource(item.icon),
                    contentDescription = null,
                    tint = Dark,
                    modifier = Modifier.size(20.dp)
                )
                AppText(
                    text = item.text,
                    fontSize = 14.sp
                )
            }
        }
    }
}

@Composable
private fun ContactActivity(transactions: List<TransactionContact>, navController: NavController) {
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        transactions.forEach {
            when (it.jenis.lowercase()) {
                "penjualan" -> ContactActivityCardSell(it, navController)
                "pembelian" -> ContactActivityCardPurchase(it, navController)
                "tagihan" -> ContactActivityCardBill(it, navController)
            }
        }
        Spacer(Modifier.height(30.dp))
    }
}


@Composable
private fun ContactActivityCardSell(transaction: TransactionContact, navController: NavController){
    val symbols = DecimalFormatSymbols(Locale("id", "ID")).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val hargaFormatter = DecimalFormat("#,###.##", symbols)

    val nominal = transaction.nominal.toDoubleOrNull() ?: 0.0
    val formattedNominal = "Rp" + hargaFormatter.format(nominal)

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID"))
    val formattedDate = try {
        val dateTime = LocalDateTime.parse(transaction.tanggal, inputFormatter)
        outputFormatter.format(dateTime)
    } catch (e: Exception) {
        "Tanggal tidak valid"
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(Color.Black.copy(0.1f), 16.dp, 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .clickable{ navController.navigate("penjualan_detail/" + transaction.id )}
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppText(transaction.id)
        HorizontalLine(1f, color =  Gray.copy(0.5f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(Primary100)
                        .padding(horizontal = 10.dp),
                ){
                    AppText(transaction.pembayaran, 12.sp, color = Primary)
                }
                AppText(formattedDate, 10.sp, color = Gray)
            }
            val nominal = transaction.nominal.toDoubleOrNull() ?: 0.0
            AppText(
                "Rp" + hargaFormatter.format(nominal),
                16.sp,
                FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun ContactActivityCardPurchase(transaction: TransactionContact, navController: NavController){
    val symbols = DecimalFormatSymbols(Locale("id", "ID")).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val hargaFormatter = DecimalFormat("#,###.##", symbols)

    val nominal = transaction.nominal.toDoubleOrNull() ?: 0.0
    val formattedNominal = "Rp" + hargaFormatter.format(nominal)

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID"))
    val formattedDate = try {
        val dateTime = LocalDateTime.parse(transaction.tanggal, inputFormatter)
        outputFormatter.format(dateTime)
    } catch (e: Exception) {
        "Tanggal tidak valid"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(Color.Black.copy(0.1f), 16.dp, 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .clickable{ navController.navigate("pembelian_detail/" + transaction.id )}
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        AppText(transaction.id)
        HorizontalLine(1f, color =  Gray.copy(0.5f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            AppText(formattedDate, 10.sp, color = Gray)
            val nominal = transaction.nominal.toDoubleOrNull() ?: 0.0
            AppText(
                "Rp" + hargaFormatter.format(nominal),
                16.sp,
                FontWeight.SemiBold,
            )
        }
    }
}

@Composable
private fun ContactActivityCardBill(transaction: TransactionContact, navController: NavController){
    val statusColor = when (transaction.status) {
        "Lunas" -> Success.copy(0.1f)
        "Diproses" -> Warning.copy(0.1f)
        "Jatuh Tempo" -> Danger.copy(0.1f)
        else -> Gray.copy(0.1f)
    }
    val statusTextColor = when (transaction.status) {
        "Lunas" -> Success
        "Diproses" -> Warning
        "Jatuh Tempo" -> Danger
        else -> Gray
    }
    val symbols = DecimalFormatSymbols(Locale("id", "ID")).apply {
        groupingSeparator = '.'
        decimalSeparator = ','
    }
    val hargaFormatter = DecimalFormat("#,###.##", symbols)

    val nominal = transaction.nominal.toDoubleOrNull() ?: 0.0
    val formattedNominal = "Rp" + hargaFormatter.format(nominal)

    val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
    val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy", Locale("id", "ID"))
    val formattedDate = try {
        val dateTime = LocalDateTime.parse(transaction.tanggal, inputFormatter)
        outputFormatter.format(dateTime)
    } catch (e: Exception) {
        "Tanggal tidak valid"
    }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .shadow(Color.Black.copy(0.1f), 16.dp, 8.dp)
            .clip(RoundedCornerShape(8.dp))
            .background(White)
            .clickable{ navController.navigate("tagihan_detail/" + transaction.id )}
            .padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        AppText(transaction.id)
        HorizontalLine(1f, color =  Gray.copy(0.5f))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Column (
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ){
                Row(
                    modifier = Modifier
                        .clip(RoundedCornerShape(100.dp))
                        .background(statusColor)
                        .padding(horizontal = 10.dp),
                ){
                    AppText(transaction.status.toString(), 12.sp, color = statusTextColor)
                }
                AppText(formattedDate, 10.sp, color = Gray)
            }
            val nominal = transaction.nominal.toDoubleOrNull() ?: 0.0
            AppText(
                "Rp" + hargaFormatter.format(nominal),
                16.sp,
                FontWeight.SemiBold,
            )
        }
    }
}