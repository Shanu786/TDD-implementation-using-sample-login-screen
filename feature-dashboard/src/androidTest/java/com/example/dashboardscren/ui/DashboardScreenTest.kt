package com.example.dashboardscren.ui

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.core.domain.model.DashboardItem
import com.example.dashboardscren.ui.model.DashboardUiState
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DashboardScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingState_displaysProgressIndicator() {
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState(isLoading = true),
                onLogoutClick = {},
                onRetryClick = {},
                onItemClick = {},
            )
        }

        composeRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate)).assertIsDisplayed()
    }

    @Test
    fun successState_displaysDashboardItems() {
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState(isLoading = false, items = listOf(sampleItem)),
                onLogoutClick = {},
                onRetryClick = {},
                onItemClick = {},
            )
        }

        composeRule.onNodeWithText("A dashboard post").assertIsDisplayed()
        composeRule.onNodeWithText("A photo title").assertIsDisplayed()
    }

    @Test
    fun emptyState_displaysEmptyMessage() {
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState(isLoading = false),
                onLogoutClick = {},
                onRetryClick = {},
                onItemClick = {},
            )
        }

        composeRule.onNodeWithText("No dashboard items available.").assertIsDisplayed()
    }

    @Test
    fun errorState_retryButtonInvokesCallback() {
        var retryClicked = false
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState(
                    isLoading = false,
                    errorMessage = "Couldn't load dashboard items. Please try again.",
                ),
                onLogoutClick = {},
                onRetryClick = { retryClicked = true },
                onItemClick = {},
            )
        }

        composeRule.onNodeWithText("Retry").performClick()
        composeRule.runOnIdle { assertTrue(retryClicked) }
    }

    @Test
    fun errorState_displaysErrorMessage() {
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState(
                    isLoading = false,
                    errorMessage = "Couldn't load dashboard items. Please try again.",
                ),
                onLogoutClick = {},
                onRetryClick = {},
                onItemClick = {},
            )
        }

        composeRule.onNodeWithText("Couldn't load dashboard items. Please try again.").assertIsDisplayed()
    }

    @Test
    fun logoutButton_invokesCallback() {
        var logoutClicked = false
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState(isLoading = false),
                onLogoutClick = { logoutClicked = true },
                onRetryClick = {},
                onItemClick = {},
            )
        }

        composeRule.onNodeWithText("Log out").performClick()
        composeRule.runOnIdle { assertTrue(logoutClicked) }
    }

    @Test
    fun clickingDashboardItem_passesSelectedItem() {
        var selectedItem: DashboardItem? = null
        composeRule.setContent {
            DashboardScreen(
                uiState = DashboardUiState(isLoading = false, items = listOf(sampleItem)),
                onLogoutClick = {},
                onRetryClick = {},
                onItemClick = { selectedItem = it },
            )
        }

        composeRule.onNodeWithText("A dashboard post").performClick()
        composeRule.runOnIdle { assertTrue(selectedItem == sampleItem) }
    }

    private companion object {
        val sampleItem = DashboardItem(
            id = 1,
            userId = 25,
            postTitle = "A dashboard post",
            body = "Post body",
            photoTitle = "A photo title",
            url = "",
            thumbnailUrl = "",
        )
    }
}
