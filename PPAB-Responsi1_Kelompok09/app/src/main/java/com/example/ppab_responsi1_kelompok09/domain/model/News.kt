package com.example.ppab_responsi1_kelompok09.domain.model

import com.google.gson.annotations.SerializedName
import java.util.Date

data class News(
    val id : String,
    val title : String,
    val description : String,
    val date : Date,
    val imageUrl : String,
    val source : String,
    val sourceIcon : String,
    val content: String,
    val creator: String,
)

data class ArticleDto(
    val title: String?,
    val article_id: String?,  // dijadikan id
    val description: String?,
    @SerializedName("pubDate")
    val pubDate: String?,
    @SerializedName("image_url")
    val image_url: String?,
    @SerializedName("source_id")
    val source_id: String?,
    @SerializedName("source_icon")
    val source_icon : String?,
    val content : String?,
    val creator: String?,
    // field lain diabaikan
)

data class NewsDataResponse(
    val status: String?,
    val results: List<ArticleDto>?,
    @SerializedName("nextPage")
    val nextPage: String?
)