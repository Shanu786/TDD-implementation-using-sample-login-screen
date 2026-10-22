package com.example.feature_details.ui.model

import com.example.core.domain.model.DashboardItem

sealed interface DetailsUiState {
    data object Loading : DetailsUiState
    data class Success(val item: DashboardItem) : DetailsUiState
    data object NotFound : DetailsUiState
    data object Error : DetailsUiState
}
