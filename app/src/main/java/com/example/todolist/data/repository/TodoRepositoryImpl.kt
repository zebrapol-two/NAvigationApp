package com.example.todolist.data.repository

import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.data.model.TodoItemDto
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository

class TodoRepositoryImpl(private val dataSource: TodoJsonDataSource) : TodoRepository {
    private val todos = mutableListOf<TodoItem>()
    private var initialized = false

    private fun ensureLoaded() {
        if (initialized) return
        val dtos = dataSource.getTodos()
        todos.clear()
        todos.addAll(dtos.map { dto ->
            TodoItem(
                id = dto.id,
                title = dto.title,
                description = dto.description,
                isCompleted = dto.isCompleted
            )
        })
        initialized = true
    }

    override suspend fun getTodos(): List<TodoItem> {
        ensureLoaded()
        return todos.toList()
    }

    override suspend fun toggleTodo(id: Int) {
        ensureLoaded()
        val index = todos.indexOfFirst { it.id == id }
        if (index >= 0) {
            val old = todos[index]
            todos[index] = old.copy(isCompleted = !old.isCompleted)
        }
    }
}

