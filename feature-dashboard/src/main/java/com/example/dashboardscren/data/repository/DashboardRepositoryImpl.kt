package com.example.dashboardscren.data.repository

import com.example.dashboardscren.data.datasource.DashboardLocalDataSource
import com.example.dashboardscren.data.model.toDomain
import com.example.core.domain.model.DashboardItem
import com.example.core.domain.repository.DashboardRepository
import javax.inject.Inject

class DashboardRepositoryImpl @Inject constructor(
    private val localDataSource: DashboardLocalDataSource,
) : DashboardRepository {
    override suspend fun getDashboardItems(): List<DashboardItem> =
        localDataSource.getDashboardItems().map { it.toDomain() }

    override suspend fun getDashboardItem(itemId: Int): DashboardItem? =
        getDashboardItems().firstOrNull { it.id == itemId }
}
