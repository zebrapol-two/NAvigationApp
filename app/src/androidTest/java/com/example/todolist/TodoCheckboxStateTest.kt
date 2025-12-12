package com.example.todolist

import androidx.compose.ui.test.assertIsOff
import androidx.compose.ui.test.assertIsOn
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.module37_8.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoCheckboxTest {

    @get:Rule
    val composeTestRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun testCheckboxForId1_InitialStateIsUnchecked() {
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("todo_checkbox_1", useUnmergedTree = true)
            .assertIsOff()
    }

    @Test
    fun testCheckboxForId1_AfterClick_BecomesChecked() {
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("todo_checkbox_1", useUnmergedTree = true)
            .performClick()

        composeTestRule.onNodeWithTag("todo_checkbox_1", useUnmergedTree = true)
            .assertIsOn()
    }

    @Test
    fun testCheckboxForId1_ToggleTwice_ReturnsToInitialState() {
        composeTestRule.waitForIdle()

        composeTestRule.onNodeWithTag("todo_checkbox_1", useUnmergedTree = true)
            .performClick()

        composeTestRule.onNodeWithTag("todo_checkbox_1", useUnmergedTree = true)
            .assertIsOn()

        composeTestRule.onNodeWithTag("todo_checkbox_1", useUnmergedTree = true)
            .performClick()

        composeTestRule.onNodeWithTag("todo_checkbox_1", useUnmergedTree = true)
            .assertIsOff()
    }
}

