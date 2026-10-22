package com.example.dashboardscren.data.model

import com.example.core.domain.model.DashboardItem
import kotlinx.serialization.Serializable

@Serializable
data class DashboardItemDto(
    val id: Int,
    val userId: Int,
    val postTitle: String,
    val body: String,
    val photoTitle: String,
    val url: String,
    val thumbnailUrl: String,
)

fun DashboardItemDto.toDomain() = DashboardItem(
    id = id,
    userId = userId,
    postTitle = postTitle,
    body = body,
    photoTitle = photoTitle,
    url = url,
    thumbnailUrl = thumbnailUrl,
)
