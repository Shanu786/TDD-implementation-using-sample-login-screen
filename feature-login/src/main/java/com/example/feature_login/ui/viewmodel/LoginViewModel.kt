package com.example.feature_login.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.feature_login.ui.datamodel.LoginUiState
import com.example.feature_login.domain.model.LoginValidationResult
import com.example.feature_login.domain.usecase.LoginUseCase
import com.example.feature_login.ui.event.LoginUiEvent
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: LoginUseCase,
) : ViewModel() {

    private val _uiState = MutableStateFlow(LoginUiState())
    val uiState = _uiState.asStateFlow()

    private val _events = Channel<LoginUiEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    fun onUsernameChanged(username: String) {
        _uiState.update {
            it.copy(username = username, usernameError = null)
        }
    }

    fun onPasswordChanged(password: String) {
        _uiState.update {
            it.copy(password = password, passwordError = null)
        }
    }

    fun submitAction() {
        val currentState = _uiState.value

        when (loginUseCase.validate(currentState.username, currentState.password)) {
            LoginValidationResult.ValidLogin -> {
                _uiState.value = currentState.copy(usernameError = null, passwordError = null)
                _events.trySend(LoginUiEvent.NavigateToDashboard)
            }

            LoginValidationResult.BothFieldsRequired -> {
                _uiState.value = currentState.copy(
                    usernameError = "Enter a username.",
                    passwordError = "Enter a password.",
                )
            }

            LoginValidationResult.UsernameRequired -> {
                _uiState.value = currentState.copy(usernameError = "Enter a username.")
            }

            LoginValidationResult.PasswordRequired -> {
                _uiState.value = currentState.copy(passwordError = "Enter a password.")
            }

            LoginValidationResult.UsernameInvalid -> {
                _uiState.value = currentState.copy(
                    usernameError = "Use only letters and numbers.",
                )
            }

            LoginValidationResult.PasswordInvalid -> {
                _uiState.value = currentState.copy(
                    passwordError = "Use uppercase and lowercase letters, a number, and a symbol.",
                )
            }

            LoginValidationResult.PasswordTooShort -> {
                _uiState.value = currentState.copy(
                    passwordError = "Password must be at least 6 characters.",
                )
            }
        }
    }
}
