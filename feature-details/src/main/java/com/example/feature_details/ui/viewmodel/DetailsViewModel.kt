package com.example.feature_details.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import com.example.feature_details.domain.usecase.GetDashboardItemUseCase
import com.example.feature_details.ui.model.DetailsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val getDashboardItem: GetDashboardItemUseCase,
) : ViewModel() {
    private val itemId: Int = checkNotNull(savedStateHandle[ITEM_ID_ARGUMENT]) {
        "The Details destination requires an itemId argument."
    }

    private val _uiState = MutableStateFlow<DetailsUiState>(DetailsUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadItem()
    }

    fun loadItem() {
        viewModelScope.launch {
            _uiState.value = DetailsUiState.Loading
            try {
                val item = getDashboardItem(itemId)
                _uiState.value = item?.let(DetailsUiState::Success) ?: DetailsUiState.NotFound
            } catch (cancellation: CancellationException) {
                throw cancellation
            } catch (_: Exception) {
                _uiState.value = DetailsUiState.Error
            }
        }
    }

    private companion object {
        const val ITEM_ID_ARGUMENT = "itemId"
    }
}
