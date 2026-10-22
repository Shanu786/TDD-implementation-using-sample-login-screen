package com.example.dashboardscren.ui.model

import com.example.core.domain.model.DashboardItem

data class DashboardUiState(
    val isLoading: Boolean = true,
    val items: List<DashboardItem> = emptyList(),
    val errorMessage: String? = null,
)
