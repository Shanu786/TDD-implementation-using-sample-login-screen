package com.example.feature_login.ui.event

sealed interface LoginUiEvent {
    data object NavigateToDashboard : LoginUiEvent
}
