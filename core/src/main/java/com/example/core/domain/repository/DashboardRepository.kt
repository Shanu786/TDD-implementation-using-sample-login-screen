package com.example.core.domain.repository

import com.example.core.domain.model.DashboardItem

interface DashboardRepository {
    suspend fun getDashboardItems(): List<DashboardItem>
    suspend fun getDashboardItem(itemId: Int): DashboardItem?
}
