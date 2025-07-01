package com.example.ppab_responsi1_kelompok09.domain.repository

import com.example.ppab_responsi1_kelompok09.data.constant.NEWS_API_KEY
import com.example.ppab_responsi1_kelompok09.domain.model.ArticleDto
import com.example.ppab_responsi1_kelompok09.domain.model.News
import java.text.SimpleDateFormat
import java.time.OffsetDateTime
import java.util.Date
import java.util.Locale

object NewsRepository {
    private val ApiKey = NEWS_API_KEY
    private val api = NewsRetrofit.apiService
    private val cache = mutableMapOf<String, News>()

    suspend fun getAll(limit: Int): List<News> {
        val result = mutableListOf<News>()

        // ambil dari cache kalo kurang lanjut fetching
        if (cache.size >= limit) {
            return cache.values.take(limit)
        } else {
            result.addAll(cache.values)
        }

        // fetching dari newsdata.io
        val firstResponse = try {
            api.getLatest(ApiKey, category = "business", country = "id")
        } catch (e: Exception) {
            return emptyList()
        }
        var response = firstResponse

        repeat(3) { iteration ->
            val articles = response.results.orEmpty()
            if (articles.isEmpty()) {
                // KOSONG
            } else {
                val mapped = articles.mapNotNull { it.toNews() }
                for (news in mapped) {
                    if (result.size >= limit) break
                    if (!cache.containsKey(news.id)) {
                        cache[news.id] = news
                        result.add(news)
                    }
                }
                if (result.size >= limit) return result.take(limit)
            }
            val nextPath = response.nextPage
            if (nextPath.isNullOrBlank()) {
                return result.take(limit)
            }
            val nextUrl = if (nextPath.startsWith("http")) {
                nextPath
            } else {
                "https://newsdata.io/api$nextPath"
            }
            response = try {
                api.getByUrl(nextUrl)
            } catch (e: Exception) {
                return result.take(limit)
            }
        }
        return result.take(limit)
    }

    /**
     * Harus digunakan ketika getAll() pernah dipanggil
     */
    fun getById(id: String): News? {
        return cache[id]
    }

    fun clearCache(){
        cache.clear()
    }
}

private fun ArticleDto.toNews(): News? {
    val id = this.article_id ?: return null // butuh id unik
    val title = this.title ?: return null
    val description = this.description ?: ""
    val dateStr = this.pubDate ?: return null
    val date = parsePubDate(dateStr) ?: return null
    val imageUrl = this.image_url ?: ""
    val source = this.source_id ?: ""
    val sourceIcon = this.source_icon ?: "" //"https://logo.clearbit.com/$source.com"
    val content = this.content ?: ""
    val creator = this.creator ?: ""

    return News(
        id = id,
        title = title,
        description = description,
        date = date,
        imageUrl = imageUrl,
        source = source,
        sourceIcon = sourceIcon,
        content = content,
        creator = creator,
    )
}

private fun parsePubDate(dateStr: String): Date? {
    return try {
        // Coba parse ISO 8601
        val odt = OffsetDateTime.parse(dateStr) // throws if format beda
        Date.from(odt.toInstant())
    } catch (_: Exception) {
        try {
            // Coba format lain, misal "yyyy-MM-dd HH:mm:ss"
            val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            sdf.parse(dateStr)
        } catch (_: Exception) {
            null
        }
    }
}