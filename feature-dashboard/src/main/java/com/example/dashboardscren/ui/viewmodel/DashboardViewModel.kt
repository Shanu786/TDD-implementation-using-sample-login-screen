package com.example.dashboardscren.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.dashboardscren.domain.usecase.GetDashboardItemsUseCase
import com.example.dashboardscren.ui.model.DashboardUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val getDashboardItems: GetDashboardItemsUseCase,
) : ViewModel() {
    private val _uiState = MutableStateFlow(DashboardUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadDashboardItems()
    }

    fun loadDashboardItems() {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true, errorMessage = null)

            try {
                val items = getDashboardItems()
                _uiState.value = DashboardUiState(isLoading = false, items = items)
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                _uiState.value = DashboardUiState(
                    isLoading = false,
                    errorMessage = "Couldn't load dashboard items. Please try again.",
                )
            }
        }
    }

}
