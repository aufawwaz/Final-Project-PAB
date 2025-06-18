package com.example.ppab_responsi1_kelompok09.presentation.contact

import com.example.ppab_responsi1_kelompok09.data.remote.RetrofitInstance.BASE_URL
import com.example.ppab_responsi1_kelompok09.domain.model.Contact

private val url = BASE_URL

fun Contact.getImageUrl(baseUrl: String = url): String? {
    val image = this.image_kontak ?: return null
    val cleanImage = image.replace("\\", "/")
    val path = if (cleanImage.startsWith("storage/")) cleanImage else "storage/$cleanImage"
    val cleanBaseUrl = if (baseUrl.endsWith("/")) baseUrl else "$baseUrl/"
    val finalPath = path.removePrefix("/")
    return cleanBaseUrl + finalPath
}