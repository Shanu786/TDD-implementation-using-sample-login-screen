package com.example.dashboardscren.data.datasource

import com.example.dashboardscren.data.model.DashboardItemDto

interface DashboardLocalDataSource {
    suspend fun getDashboardItems(): List<DashboardItemDto>
}
