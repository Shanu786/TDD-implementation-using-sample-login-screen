package com.example.feature_login.utils

private val USERNAME_REGEX = Regex("^[A-Za-z0-9]+$")

const val MIN_PASSWORD_LENGTH = 6

fun isValidUsername(username: String): Boolean {
    return username.isNotBlank() &&
            USERNAME_REGEX.matches(username)
}

fun isValidPassword(password: String): Boolean =
    password.any { it.isUpperCase() } &&
        password.any { it.isLowerCase() } &&
        password.any { it.isDigit() } &&
        password.any { !it.isLetterOrDigit() }
