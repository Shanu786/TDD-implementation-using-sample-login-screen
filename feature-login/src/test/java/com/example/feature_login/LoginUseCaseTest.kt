package com.example.feature_login

import com.example.feature_login.domain.model.LoginValidationResult
import com.example.feature_login.domain.usecase.LoginUseCase
import org.junit.Assert.assertEquals
import org.junit.Test

class LoginUseCaseTest {
    private val loginUseCase = LoginUseCase()

    @Test
    fun blankFields_returnBothFieldsRequired() {
        assertEquals(
            LoginValidationResult.BothFieldsRequired,
            loginUseCase.validate("", ""),
        )
    }

    @Test
    fun whitespaceUsername_isTreatedAsMissing() {
        assertEquals(
            LoginValidationResult.UsernameRequired,
            loginUseCase.validate("   ", "Abc123!"),
        )
    }

    @Test
    fun blankPassword_returnsPasswordRequired() {
        assertEquals(
            LoginValidationResult.PasswordRequired,
            loginUseCase.validate("user.name_1", ""),
        )
    }

    @Test
    fun unsupportedUsernameCharacters_returnUsernameInvalid() {
        assertEquals(
            LoginValidationResult.UsernameInvalid,
            loginUseCase.validate("user-name", "Abc123!"),
        )
    }

    @Test
    fun usernameWithWhitespace_returnsUsernameInvalid() {
        assertEquals(
            LoginValidationResult.UsernameInvalid,
            loginUseCase.validate(" user ", "Abc123!"),
        )
    }

    @Test
    fun passwordShorterThanSixCharacters_returnsPasswordTooShort() {
        assertEquals(
            LoginValidationResult.PasswordTooShort,
            loginUseCase.validate("username", "Ab1!x"),
        )
    }

    @Test
    fun passwordExactlySixCharacters_canPassLengthRule() {
        assertEquals(
            LoginValidationResult.ValidLogin,
            loginUseCase.validate("username", "Ab1!xy"),
        )
    }

    @Test
    fun passwordMissingRequiredCharacterTypes_returnsPasswordInvalid() {
        assertEquals(
            LoginValidationResult.PasswordInvalid,
            loginUseCase.validate("username", "password123"),
        )
    }

    @Test
    fun validUsernameAndPassword_returnValid() {
        assertEquals(
            LoginValidationResult.ValidLogin,
            loginUseCase.validate("user.name_1", "Abc123!"),
        )
    }
}
