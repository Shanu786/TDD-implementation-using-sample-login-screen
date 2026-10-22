package com.example.dashboardscren.data.model

import kotlinx.serialization.json.Json

internal object DashboardItemJsonDecoder {
    private val json = Json { ignoreUnknownKeys = true }

    fun decode(jsonData: String): List<DashboardItemDto> = json.decodeFromString(jsonData)
}
