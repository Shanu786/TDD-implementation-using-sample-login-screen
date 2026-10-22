package com.example.login_screen_tdd.navigation

import kotlinx.serialization.Serializable

@Serializable
data object LoginRoute

@Serializable
data object DashboardRoute

@Serializable
data class DetailsRoute(val itemId: Int)
