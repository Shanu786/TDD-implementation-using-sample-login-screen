package com.example.feature_login.domain.model

sealed interface LoginValidationResult {
    data object ValidLogin : LoginValidationResult
    data object BothFieldsRequired : LoginValidationResult
    data object UsernameRequired : LoginValidationResult
    data object PasswordRequired : LoginValidationResult
    data object UsernameInvalid : LoginValidationResult
    data object PasswordInvalid : LoginValidationResult
    data object PasswordTooShort : LoginValidationResult
}
