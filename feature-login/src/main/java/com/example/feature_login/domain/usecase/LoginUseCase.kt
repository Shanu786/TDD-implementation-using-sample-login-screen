package com.example.feature_login.domain.usecase

import com.example.feature_login.domain.model.LoginValidationResult
import com.example.feature_login.utils.MIN_PASSWORD_LENGTH
import com.example.feature_login.utils.isValidPassword
import com.example.feature_login.utils.isValidUsername
import javax.inject.Inject

class LoginUseCase @Inject constructor() {

    fun validate(username: String, password: String): LoginValidationResult {

        if (username.isBlank() && password.isBlank()) {
            return LoginValidationResult.BothFieldsRequired
        }

        if (username.isBlank()) return LoginValidationResult.UsernameRequired

        if (password.isBlank()) return LoginValidationResult.PasswordRequired

        if (!isValidUsername(username)) return LoginValidationResult.UsernameInvalid

        if (password.length < MIN_PASSWORD_LENGTH) {
            return LoginValidationResult.PasswordTooShort
        }

        if (!isValidPassword(password)) return LoginValidationResult.PasswordInvalid

        return LoginValidationResult.ValidLogin
    }
}
