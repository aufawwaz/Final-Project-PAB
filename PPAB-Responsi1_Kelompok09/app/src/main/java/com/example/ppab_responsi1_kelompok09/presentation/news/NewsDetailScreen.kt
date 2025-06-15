package com.example.ppab_responsi1_kelompok09.presentation.news

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.ppab_responsi1_kelompok09.R
import com.example.ppab_responsi1_kelompok09.domain.repository.NewsRepository
import com.example.ppab_responsi1_kelompok09.presentation.components.AppText
import com.example.ppab_responsi1_kelompok09.presentation.components.HeaderPageOnBack
import com.example.ppab_responsi1_kelompok09.ui.theme.Gray
import com.example.ppab_responsi1_kelompok09.ui.theme.White
import java.text.SimpleDateFormat
import java.util.Locale

@Composable
fun NewsDetailScreen(navController: NavController, newsId: String){
    val news = NewsRepository.getById(newsId)

    if (news == null){
        Column(
            modifier = Modifier.fillMaxSize().background(White).padding(vertical = 20.dp),
        ) {
            Column {
                HeaderPageOnBack({navController.popBackStack()}, "Baca Berita")
            }
            Column (
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                AppText("(>_<)", 32.sp, FontWeight.SemiBold, Gray)
                AppText(
                    "Terjadi Kesalahan saat membuka berita",
                    color = Gray,
                    textAlign = TextAlign.Center
                )
            }
        }
    } else{
        val dateFormatter = SimpleDateFormat("dd MMMM yyyy", Locale("id", "ID"))

        Spacer(Modifier.height(20.dp))
        Column(
            modifier = Modifier.fillMaxSize().background(White).padding(vertical = 20.dp),
        ) {
            Column {
                HeaderPageOnBack({navController.popBackStack()}, "Baca Berita")
            }
            Column (
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp),
            ){
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp).fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    AppText(news.title, 18.sp, FontWeight.Bold, lineHeight = 24.sp)
                    AppText(dateFormatter.format(news.date))
                    Row ( verticalAlignment = Alignment.CenterVertically){
                        AsyncImage(
                            model = news.sourceIcon,
                            placeholder = painterResource(R.drawable.ic_logo),
                            contentDescription = null,
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.padding(horizontal = 4.dp).size(24.dp),
                        )
                        Column {
                            AppText(news.source, 12.sp, FontWeight.SemiBold)
                        }
                    }
                }
                AsyncImage(
                    model = news.imageUrl,
                    placeholder = painterResource(R.drawable.img_full_logo),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(200.dp),
                )
                Column(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    AppText(news.description, lineHeight = 18.sp)
                }
                Spacer(Modifier.height(15.dp))
            }
        }
    }
}