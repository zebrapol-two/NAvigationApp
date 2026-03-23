package com.example.todolist.data.preferences

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.todoPreferencesDataStore by preferencesDataStore(name = "todo_preferences")

class TodoPreferencesRepository(private val context: Context) {
    private object Keys {
        val highlightCompleted = booleanPreferencesKey("highlight_completed")
    }

    val highlightCompletedFlow: Flow<Boolean> = context.todoPreferencesDataStore.data
        .map { preferences: Preferences ->
            preferences[Keys.highlightCompleted] ?: true
        }

    suspend fun setHighlightCompleted(value: Boolean) {
        context.todoPreferencesDataStore.edit { preferences ->
            preferences[Keys.highlightCompleted] = value
        }
    }
}

