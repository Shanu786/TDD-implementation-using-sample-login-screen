package com.example.feature_details.domain.usecase

import com.example.core.domain.model.DashboardItem
import com.example.core.domain.repository.DashboardRepository
import javax.inject.Inject

class GetDashboardItemUseCase @Inject constructor(
    private val dashboardRepository: DashboardRepository,
) {
    suspend operator fun invoke(itemId: Int): DashboardItem? =
        dashboardRepository.getDashboardItem(itemId)
}
