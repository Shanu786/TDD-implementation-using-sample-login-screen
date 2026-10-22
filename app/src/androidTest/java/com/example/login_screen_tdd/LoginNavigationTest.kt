package com.example.login_screen_tdd

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTextInput
import android.content.Intent
import androidx.activity.ComponentActivity
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LoginNavigationTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<ComponentActivity>() // Start Activity

    @Before
    fun launchAppActivityFromTestHost() {
        composeRule.activityRule.scenario.onActivity { testHost ->
            testHost.startActivity(Intent(testHost, MainActivity::class.java))
        }
    }

    @Test
    fun bothFieldsEmpty_showsRequiredErrors() {
        submitLogin()

        composeRule.onNodeWithText("Enter a username.").assertIsDisplayed()
        composeRule.onNodeWithText("Enter a password.").assertIsDisplayed()
        assertStillOnLoginScreen()
    }

    @Test
    fun usernameEmpty_showsUsernameRequiredError() {
        enterPassword(VALID_PASSWORD)
        submitLogin()

        composeRule.onNodeWithText("Enter a username.").assertIsDisplayed()
        assertStillOnLoginScreen()
    }

    @Test
    fun passwordEmpty_showsPasswordRequiredError() {
        enterUsername(VALID_USERNAME)
        submitLogin()

        composeRule.onNodeWithText("Enter a password.").assertIsDisplayed()
        assertStillOnLoginScreen()
    }

    @Test
    fun usernameHasUnsupportedCharacter_showsUsernameFormatError() {
        enterUsername("user-name")
        enterPassword(VALID_PASSWORD)
        submitLogin()

        composeRule.onNodeWithText(
            "Use only letters, numbers, dots, or underscores.",
        ).assertIsDisplayed()
        assertStillOnLoginScreen()
    }

    @Test
    fun passwordDoesNotMeetCharacterRequirements_showsPasswordFormatError() {
        enterUsername(VALID_USERNAME)
        enterPassword("abcdef1")
        submitLogin()

        composeRule.onNodeWithText(
            "Use uppercase and lowercase letters, a number, and a symbol.",
        ).assertIsDisplayed()
        assertStillOnLoginScreen()
    }

    @Test
    fun passwordTooShort_showsMinimumLengthError() {
        enterUsername(VALID_USERNAME)
        enterPassword("Ab1!")
        submitLogin()

        composeRule.onNodeWithText("Password must be at least 6 characters.").assertIsDisplayed()
        assertStillOnLoginScreen()
    }

    @Test
    fun validLogin_navigatesToDashboard() {
        enterUsername(VALID_USERNAME)
        enterPassword(VALID_PASSWORD)
        submitLogin()

        composeRule.onNodeWithText("Dashboard").assertIsDisplayed()

        val firstItemTitle = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule
                .onAllNodesWithText(firstItemTitle, substring = true)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeRule
            .onNodeWithText(firstItemTitle, substring = true)
            .assertIsDisplayed()
    }

    @Test
    fun selectingDashboardItem_opensDetailsForSelectedItem() {
        enterUsername(VALID_USERNAME)
        enterPassword(VALID_PASSWORD)
        submitLogin()

        val selectedItemTitle = "sunt aut facere repellat provident occaecati excepturi optio reprehenderit"
        val selectedItemBody = "quia et suscipit"
        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule
                .onAllNodesWithText(selectedItemTitle, substring = true)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }

        composeRule.onNodeWithText(selectedItemTitle, substring = true).performClick()

        composeRule.waitUntil(timeoutMillis = 5_000) {
            composeRule
                .onAllNodesWithText(selectedItemBody, substring = true)
                .fetchSemanticsNodes()
                .isNotEmpty()
        }
        composeRule.onNodeWithText("Details").assertIsDisplayed()
        composeRule.onNodeWithText(selectedItemBody, substring = true).assertIsDisplayed()
    }

    private fun enterUsername(username: String) {
        composeRule.onNodeWithText("Enter User Name").performTextInput(username)
    }

    private fun enterPassword(password: String) {
        composeRule.onNodeWithText("Password").performTextInput(password)
    }

    private fun submitLogin() {
        composeRule.onNodeWithText("SUBMIT").performClick()
    }

    private fun assertStillOnLoginScreen() {
        composeRule.onNodeWithText("SUBMIT").assertIsDisplayed()
    }

    private companion object {
        const val VALID_USERNAME = "user.name1"
        const val VALID_PASSWORD = "Abc123@"
    }
}
