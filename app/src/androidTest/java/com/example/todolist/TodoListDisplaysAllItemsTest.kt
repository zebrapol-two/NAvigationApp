package com.example.todolist

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onAllNodesWithText
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.assertCountEquals
import androidx.compose.ui.test.assertIsDisplayed
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.module37_8.MainActivity
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TodoListDisplaysAllItemsTest {

    @get:Rule
    val composeRule = createAndroidComposeRule<MainActivity>()

    @Test
    fun todoList_displaysAllThreeItemsFromJson() {
        composeRule.onNodeWithText("Купить молоко").assertIsDisplayed()
        composeRule.onNodeWithText("Позвонить маме").assertIsDisplayed()
        composeRule.onNodeWithText("Сделать ДЗ по Android").assertIsDisplayed()

        composeRule.onAllNodesWithText("Купить молоко").assertCountEquals(1)
        composeRule.onAllNodesWithText("Позвонить маме").assertCountEquals(1)
        composeRule.onAllNodesWithText("Сделать ДЗ по Android").assertCountEquals(1)
    }
}
