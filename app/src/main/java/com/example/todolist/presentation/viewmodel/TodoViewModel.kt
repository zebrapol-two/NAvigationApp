package com.example.todolist.presentation.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository
import com.example.todolist.domain.usecase.GetTodosUseCase
import com.example.todolist.domain.usecase.ToggleTodoUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class TodoViewModel(application: Application, private val repository: TodoRepository) : AndroidViewModel(application) {
    private val getTodosUseCase = GetTodosUseCase(repository)
    private val toggleTodoUseCase = ToggleTodoUseCase(repository)

    private val _todos = MutableStateFlow<List<TodoItem>>(emptyList())
    val todos: StateFlow<List<TodoItem>> = _todos

    init {
        viewModelScope.launch {
            _todos.value = getTodosUseCase()
        }
    }

    fun toggle(id: Int) {
        viewModelScope.launch {
            toggleTodoUseCase(id)
            _todos.value = getTodosUseCase()
        }
    }

    fun getById(id: Int): TodoItem? = _todos.value.firstOrNull { it.id == id }
}
