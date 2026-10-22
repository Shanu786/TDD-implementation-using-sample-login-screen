package com.example.core.domain.model

data class DashboardItem(
    val id: Int,
    val userId: Int,
    val postTitle: String,
    val body: String,
    val photoTitle: String,
    val url: String,
    val thumbnailUrl: String,
)
