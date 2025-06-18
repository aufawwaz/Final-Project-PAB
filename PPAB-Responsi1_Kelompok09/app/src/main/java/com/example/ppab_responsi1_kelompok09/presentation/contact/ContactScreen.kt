package com.example.ppab_responsi1_kelompok09.presentation.contact

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.data.local.TokenDataStore
import com.example.ppab_responsi1_kelompok09.data.remote.RetrofitInstance
import com.example.ppab_responsi1_kelompok09.data.repository.ContactRepositoryImpl
import com.example.ppab_responsi1_kelompok09.domain.model.Contact
import com.example.ppab_responsi1_kelompok09.domain.usecase.GetContactsUseCase
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.BottomSpacer
import com.example.ppab_responsi1_kelompok09.presentation.components.HorizontalLine
import com.example.ppab_responsi1_kelompok09.presentation.components.InputTextForm
import com.example.ppab_responsi1_kelompok09.presentation.components.PageHeader
import com.example.ppab_responsi1_kelompok09.presentation.components.PageHeaderLoadingState
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.placeholder
import com.google.accompanist.placeholder.placeholder
import com.google.accompanist.placeholder.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactScreen(
    navController: NavController,
    token: String?
) {
    val viewModel: ContactViewModel? = if (token != null) {
        val repository = remember { ContactRepositoryImpl(RetrofitInstance.contactApi) }
        val useCase = remember { GetContactsUseCase(repository) }
        viewModel(factory = ContactViewModelFactory(useCase, token))
    } else null

    if (viewModel == null) {
        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
        return
    }

    val contacts by viewModel.contacts.collectAsState()
    val loading by viewModel.loading.collectAsState()
    val error by viewModel.error.collectAsState()
    val isLastPage by viewModel.isLastPage.collectAsState()
    val currentPage by viewModel.currentPage.collectAsState()
    val totalContacts by viewModel.totalContacts.collectAsState()

    var contactSearchValue by rememberSaveable { mutableStateOf("") }
    var isSearching by remember { mutableStateOf(false) }
    var filteredContact by remember { mutableStateOf(contacts) }

    LaunchedEffect(contactSearchValue, contacts) {
        isSearching = true
        filteredContact = if (contactSearchValue.isBlank()) contacts
        else contacts.filter {
            it.nama_kontak.contains(contactSearchValue, ignoreCase = true) ||
            (it.nomor_handphone ?: "").contains(contactSearchValue, ignoreCase = true)
        }
        isSearching = false
    }

//    LaunchedEffect(Unit) {
//        viewModel.fetchContacts(1)
//    }

    val listState = rememberLazyListState()

    // Lazy load next page
    LaunchedEffect(listState) {
        snapshotFlow { listState.layoutInfo.visibleItemsInfo.lastOrNull()?.index }
            .collect { lastVisibleItemIndex ->
                val totalItems = listState.layoutInfo.totalItemsCount
                if (lastVisibleItemIndex != null &&
                    lastVisibleItemIndex >= totalItems - 2 &&
                    !loading && !isLastPage
                ) {
                    viewModel.loadNextPage()
                }
            }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier.fillMaxSize().background(White),
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            if (loading == true) {
                PageHeaderLoadingState("Kontak")
            } else {
                PageHeader(
                    "Kontak",
                    "Total Kontak",
                    iconRes = R.drawable.ic_pelanggan_fill,
                    "$totalContacts Kontak"
                )
            }

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                InputTextForm(
                    contactSearchValue,
                    { contactSearchValue = it },
                    "Search Contact",
                    R.drawable.ic_search
                )

                when {
                    loading && contacts.isEmpty() -> LazyLoading()
                    isSearching -> LazyLoading()
                    else -> ShowContact(navController, filteredContact, listState, loading)
                }
            }
        }

        // Error overlay
        if (!error.isNullOrEmpty()) {
            Box(
                Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                AppText(text = "Error: $error", fontSize = 16.sp, color = Gray)
            }
        }
    }
}

@Composable
private fun ShowContact(
    navController: NavController,
    contacts: List<Contact>,
    listState: LazyListState,
    loading: Boolean
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        userScrollEnabled = true,
        state = listState
    ) {
        items(contacts) { item ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { navController.navigate("contact_detail/${item.id}") }
                    .padding(8.dp)
            ) {
                if (item.image_kontak != null) {
                    AsyncImage(
                        model = ImageRequest.Builder(LocalContext.current)
                            .data(item.getImageUrl())
                            .diskCachePolicy(CachePolicy.DISABLED)
                            .build(),
                        contentDescription = null,
                        contentScale = ContentScale.Crop,
                        placeholder = painterResource(id = R.drawable.img_profile_picture),
                        error = painterResource(id = R.drawable.img_profile_picture),
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                    )
                } else {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(CircleShape)
                            .background(Color(0XFFEDEDEF)),
                        contentAlignment = Alignment.Center
                    ) {
                        AppText(
                            text = item.nama_kontak.substring(0, 1).uppercase(),
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                    }
                }
                Column(modifier = Modifier.padding(start = 8.dp)) {
                    AppText(item.nama_kontak, 14.sp)
                    AppText(item.nomor_handphone ?: "-", 12.sp, color = Gray)
                }
            }
            HorizontalLine(1f, color = Gray.copy(0.1f))
        }
        if (loading && contacts.isNotEmpty()) {
            items(3) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .placeholder(
                                visible = true,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = Color(0xFFBBBBBB)
                                )
                            )
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Column {
                        Box(
                            modifier = Modifier
                                .width(150.dp)
                                .height(14.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .placeholder(
                                    visible = true,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(4.dp),
                                    highlight = PlaceholderHighlight.shimmer(
                                        highlightColor = Color(0xFFBBBBBB)
                                    )
                                )
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Box(
                            modifier = Modifier
                                .width(100.dp)
                                .height(12.dp)
                                .clip(RoundedCornerShape(4.dp))
                                .placeholder(
                                    visible = true,
                                    color = Color.LightGray,
                                    shape = RoundedCornerShape(4.dp),
                                    highlight = PlaceholderHighlight.shimmer(
                                        highlightColor = Color(0xFFBBBBBB)
                                    )
                                )
                        )
                    }
                }
            }
        }
        item { BottomSpacer() }
    }
}

@Composable
private fun LazyLoading() {
    Column(modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp)) {
        repeat(10) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(20.dp))
                        .placeholder(
                            visible = true,
                            color = Color.LightGray,
                            shape = RoundedCornerShape(4.dp),
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color(0xFFBBBBBB)
                            )
                        )
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column {
                    Box(
                        modifier = Modifier
                            .width(150.dp)
                            .height(14.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .placeholder(
                                visible = true,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = Color(0xFFBBBBBB)
                                )
                            )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Box(
                        modifier = Modifier
                            .width(100.dp)
                            .height(12.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .placeholder(
                                visible = true,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(4.dp),
                                highlight = PlaceholderHighlight.shimmer(
                                    highlightColor = Color(0xFFBBBBBB)
                                )
                            )
                    )
                }
            }
        }
    }
}