package com.example.ppab_responsi1_kelompok09.data.remote.dto

import com.example.ppab_responsi1_kelompok09.domain.model.Contact
import com.google.gson.annotations.SerializedName

data class ContactResponse(
    val current_page: Int,
    val data: List<Contact>,
    val first_page_url: String,
    val from: Int,
    val last_page: Int,
    val last_page_url: String,
    val links: List<Link>?,
    val next_page_url: String?,
    val path: String,
    val per_page: Int,
    val prev_page_url: String?,
    val to: Int,
    val total: Int
)

data class Link(
    val url: String?,
    val label: String,
    val active: Boolean
)