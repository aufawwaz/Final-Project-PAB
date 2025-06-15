package com.example.ppab_responsi1_kelompok09.presentation.news

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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ppab_responsi1_kelompok09.domain.model.News
import com.example.ppab_responsi1_kelompok09.domain.repository.NewsRepository
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.HeaderPageOnBack
import com.example.ppab_responsi1_kelompok09.presentation.components.HorizontalLine
import com.example.ppab_responsi1_kelompok09.ui.theme.Danger
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material3.placeholder
import com.google.accompanist.placeholder.material3.shimmer
import com.google.accompanist.placeholder.shimmer
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NewsScreen(
    navController: NavController
){
    var newsList by remember { mutableStateOf<List<News>>(emptyList()) }
    var isLoading by remember { mutableStateOf(true) }
    var errorMsg by remember { mutableStateOf<String?>(null) }
    LaunchedEffect(Unit) {
        try {
            newsList = NewsRepository.getAll(21)
        } catch(e: Exception) {
            errorMsg = "Gagal memuat berita"
        } finally {
            isLoading = false
        }
    }
    if (isLoading) {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 20.dp),
        ){
            HeaderPageOnBack({ navController.popBackStack() }, "Baca Berita")
            Column (
                modifier = Modifier.fillMaxWidth().fillMaxHeight(0.85f),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ){
                Spacer(Modifier.height(10.dp))
                NewsCardLoading()
                NewsCardLoading()
                Spacer(Modifier.height(10.dp))
            }
        }
    } else if (errorMsg != null) {
        AppText(errorMsg!!, color = Danger)
    } else {
        Column(
            modifier = Modifier.fillMaxSize().padding(vertical = 20.dp),
        ){
            HeaderPageOnBack({ navController.popBackStack() }, "Baca Berita")
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                item{Spacer(Modifier.height(10.dp))}

                items(newsList) { news ->
                    NewsCard(navController, news)
                }
                item{Spacer(Modifier.height(10.dp))}

            }
        }
    }
}

@Composable
private fun NewsCard(
    navController: NavController,
    news: News
){
    val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

    Column(
        modifier = Modifier.clickable{ navController.navigate("news_detail/" + news.id ) }.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ){
        AsyncImage(
            model = news.imageUrl,
            placeholder = ColorPainter(Gray),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxWidth().height(200.dp),
        )
        Column (
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ){
            AppText(
                news.title,
                18.sp,
                FontWeight.Bold,
                lineHeight = 22.sp,
                maxLines = 3
            )
            AppText(
                dateFormatter.format(news.date) + " - " + news.description,
                12.sp,
                lineHeight = 18.sp,
                maxLines = 4
            )
            Row (
                verticalAlignment = Alignment.CenterVertically
            ){
                AsyncImage(
                    model = news.sourceIcon,
                    placeholder = ColorPainter(Gray),
                    contentDescription = null,
                    modifier = Modifier.padding(horizontal = 4.dp).size(22.dp),
                )
                AppText(
                    news.source,
                    12.sp,
                    color = Gray,
                )
            }
        }

    }
    Spacer(Modifier.height(8.dp))
    HorizontalLine(1f)
}

@Composable
fun NewsCardLoading() {
    Column(
        modifier = Modifier
            .fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Gambar berita
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp)
                .placeholder(
                    visible = true,
                    color = Gray,
                    highlight = PlaceholderHighlight.shimmer(
                        highlightColor = Color(0xFFBBBBBB)
                    ),
                    shape = RectangleShape
                )
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            // Judul
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(24.dp)
                    .placeholder(
                        visible = true,
                        color = Gray,
                        highlight = PlaceholderHighlight.shimmer(
                            highlightColor = Color(0xFFBBBBBB)
                        ),
                        shape = RoundedCornerShape(4.dp)
                    )
            )

            // Deskripsi
            repeat(2) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(16.dp)
                        .placeholder(
                            visible = true,
                            color = Gray,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color(0xFFBBBBBB)
                            ),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }

            // Source row
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .size(22.dp)
                        .placeholder(
                            visible = true,
                            color = Gray,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color(0xFFBBBBBB)
                            ),
                            shape = CircleShape
                        )
                )
                Spacer(Modifier.width(8.dp))
                Box(
                    modifier = Modifier
                        .width(100.dp)
                        .height(16.dp)
                        .placeholder(
                            visible = true,
                            color = Gray,
                            highlight = PlaceholderHighlight.shimmer(
                                highlightColor = Color(0xFFBBBBBB)
                            ),
                            shape = RoundedCornerShape(4.dp)
                        )
                )
            }
        }
    }

    Spacer(Modifier.height(8.dp))
    HorizontalLine(1f)
}