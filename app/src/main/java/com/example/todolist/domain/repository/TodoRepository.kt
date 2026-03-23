package com.example.todolist.domain.repository

import com.example.todolist.domain.model.TodoItem

interface TodoRepository {
    suspend fun getTodos(): List<TodoItem>
    suspend fun toggleTodo(id: Int)
    suspend fun addTodo(title: String, description: String)
    suspend fun updateTodo(item: TodoItem)
    suspend fun deleteTodo(id: Int)
}
