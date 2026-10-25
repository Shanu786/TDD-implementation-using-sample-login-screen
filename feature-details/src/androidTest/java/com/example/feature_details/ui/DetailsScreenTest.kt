package com.example.feature_details.ui

import androidx.compose.ui.semantics.ProgressBarRangeInfo
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasProgressBarRangeInfo
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import com.example.core.domain.model.DashboardItem
import com.example.feature_details.ui.model.DetailsUiState
import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test

class DetailsScreenTest {
    @get:Rule
    val composeRule = createComposeRule()

    @Test
    fun loadingState_displaysProgressIndicator() {
        composeRule.setContent {
            DetailsScreen(
                uiState = DetailsUiState.Loading,
                onLogoutClick = {},
                onBackClick = {},
                onRetryClick = {},
            )
        }

        composeRule.onNode(hasProgressBarRangeInfo(ProgressBarRangeInfo.Indeterminate))
            .assertIsDisplayed()
    }

    @Test
    fun successState_displaysItemDetails() {
        composeRule.setContent {
            DetailsScreen(
                uiState = DetailsUiState.Success(sampleItem),
                onLogoutClick = {},
                onBackClick = {},
                onRetryClick = {},
            )
        }

        composeRule.onNodeWithText("A dashboard post").assertIsDisplayed()
        composeRule.onNodeWithText("A photo title").assertIsDisplayed()
        composeRule.onNodeWithText("The complete item description.").assertIsDisplayed()
    }

    @Test
    fun notFoundState_displaysMessage() {
        composeRule.setContent {
            DetailsScreen(
                uiState = DetailsUiState.NotFound,
                onLogoutClick = {},
                onBackClick = {},
                onRetryClick = {},
            )
        }

        composeRule.onNodeWithText("The selected item could not be found.").assertIsDisplayed()
    }

    @Test
    fun errorState_retryButtonInvokesCallback() {
        var retryClicked = false
        composeRule.setContent {
            DetailsScreen(
                uiState = DetailsUiState.Error,
                onLogoutClick = {},
                onBackClick = {},
                onRetryClick = { retryClicked = true },
            )
        }

        composeRule.onNodeWithText("Retry").performClick()
        composeRule.runOnIdle { assertTrue(retryClicked) }
    }

    @Test
    fun backButton_invokesCallback() {
        var backClicked = false
        composeRule.setContent {
            DetailsScreen(
                uiState = DetailsUiState.NotFound,
                onLogoutClick = {},
                onBackClick = { backClicked = true },
                onRetryClick = {},
            )
        }

        composeRule.onNodeWithText("Back").performClick()
        composeRule.runOnIdle { assertTrue(backClicked) }
    }

    @Test
    fun logoutButton_invokesCallback() {
        var logoutClicked = false
        composeRule.setContent {
            DetailsScreen(
                uiState = DetailsUiState.NotFound,
                onLogoutClick = { logoutClicked = true },
                onBackClick = {},
                onRetryClick = {},
            )
        }

        composeRule.onNodeWithText("Log out").performClick()
        composeRule.runOnIdle { assertTrue(logoutClicked) }
    }

    private companion object {
        val sampleItem = DashboardItem(
            id = 7,
            userId = 25,
            postTitle = "A dashboard post",
            body = "The complete item description.",
            photoTitle = "A photo title",
            url = "",
            thumbnailUrl = "",
        )
    }
}
