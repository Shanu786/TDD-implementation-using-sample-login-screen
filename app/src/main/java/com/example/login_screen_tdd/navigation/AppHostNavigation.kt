package com.example.login_screen_tdd.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.dashboardscren.ui.DashboardScreen
import com.example.feature_details.ui.DetailsScreen
import com.example.feature_login.ui.LoginScreen

@Composable
fun AppHostNavigation(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = LoginRoute
    ) {
        composable<LoginRoute> {
            LoginScreen(
                onLoginSuccess = {
                    navController.navigate(DashboardRoute) {
                        popUpTo<LoginRoute> { inclusive = true }
                        launchSingleTop = true
                    }
                },
            )
        }

        composable<DashboardRoute> {
            DashboardScreen(
                onLogoutClick = {
                    navController.navigate(LoginRoute) {
                        popUpTo<DashboardRoute> { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onItemClick = { item ->
                    navController.navigate(DetailsRoute(itemId = item.id))
                }
            )
        }

        composable<DetailsRoute> {
            DetailsScreen(
                onLogoutClick = {
                    navController.navigate(LoginRoute) {
                        popUpTo<DashboardRoute> { inclusive = true }
                        launchSingleTop = true
                    }
                },
                onBackClick = { navController.popBackStack() }
            )
        }
    }
}
