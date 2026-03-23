package com.example.todolist.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.data.preferences.TodoPreferencesRepository
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository
import com.example.todolist.domain.usecase.AddTodoUseCase
import com.example.todolist.domain.usecase.DeleteTodoUseCase
import com.example.todolist.domain.usecase.GetTodosUseCase
import com.example.todolist.domain.usecase.ToggleTodoUseCase
import com.example.todolist.domain.usecase.UpdateTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class TodoViewModel(
    application: Application,
    private val repository: TodoRepository,
    private val preferencesRepository: TodoPreferencesRepository
) : AndroidViewModel(application) {
    private val getTodosUseCase = GetTodosUseCase(repository)
    private val toggleTodoUseCase = ToggleTodoUseCase(repository)
    private val addTodoUseCase = AddTodoUseCase(repository)
    private val updateTodoUseCase = UpdateTodoUseCase(repository)
    private val deleteTodoUseCase = DeleteTodoUseCase(repository)

    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos.asStateFlow()

    private val _highlightCompleted = MutableStateFlow(true)
    val highlightCompleted: StateFlow<Boolean> = _highlightCompleted.asStateFlow()

    init {
        viewModelScope.launch { reloadTodos() }
        viewModelScope.launch {
            preferencesRepository.highlightCompletedFlow.collect {
                _highlightCompleted.value = it
            }
        }
    }

    fun refresh() {
        viewModelScope.launch { reloadTodos() }
    }

    fun toggle(id: Int) {
        viewModelScope.launch {
            toggleTodoUseCase(id)
            reloadTodos()
        }
    }

    fun add(title: String, description: String) {
        viewModelScope.launch {
            if (title.isBlank()) return@launch
            addTodoUseCase(title.trim(), description.trim())
            reloadTodos()
        }
    }

    fun update(item: TodoItem) {
        viewModelScope.launch {
            updateTodoUseCase(item)
            reloadTodos()
        }
    }

    fun delete(id: Int) {
        viewModelScope.launch {
            deleteTodoUseCase(id)
            reloadTodos()
        }
    }

    fun setHighlightCompleted(value: Boolean) {
        viewModelScope.launch {
            preferencesRepository.setHighlightCompleted(value)
        }
    }

    fun getById(id: Int): TodoItem? = _todos.value.firstOrNull { it.id == id }

    private suspend fun reloadTodos() {
        _todos.value = getTodosUseCase()
    }
}
