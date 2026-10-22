package com.example.dashboardscren.domain.usecase

import com.example.core.domain.model.DashboardItem
import com.example.core.domain.repository.DashboardRepository
import javax.inject.Inject

class GetDashboardItemsUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository,
) {
    suspend operator fun invoke(): List<DashboardItem> =
        dashboardRepository.getDashboardItems()
}
