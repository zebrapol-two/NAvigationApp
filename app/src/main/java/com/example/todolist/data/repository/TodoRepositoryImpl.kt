package com.example.todolist.data.repository

import com.example.todolist.data.local.TodoDao
import com.example.todolist.data.local.toDomain
import com.example.todolist.data.local.toEntity
import com.example.todolist.data.local.TodoJsonDataSource
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository

class TodoRepositoryImpl(
    private val todoDao: TodoDao,
    private val dataSource: TodoJsonDataSource
) : TodoRepository {

    private var initialized = false

    private suspend fun ensureLoaded() {
        if (initialized) return
        if (todoDao.count() == 0) {
            val entities = dataSource.getTodos().map { dto ->
                com.example.todolist.data.local.TodoEntity(
                    id = dto.id,
                    title = dto.title,
                    description = dto.description,
                    isCompleted = dto.isCompleted
                )
            }
            todoDao.insertAll(entities)
        }
        initialized = true
    }

    override suspend fun getTodos(): List<TodoItem> {
        ensureLoaded()
        return todoDao.getAll().map { it.toDomain() }
    }

    override suspend fun toggleTodo(id: Int) {
        ensureLoaded()
        val item = todoDao.getById(id) ?: return
        todoDao.setCompleted(id, !item.isCompleted)
    }

    override suspend fun addTodo(title: String, description: String) {
        ensureLoaded()
        val nextId = (todoDao.getMaxId() ?: 0) + 1
        todoDao.insert(
            com.example.todolist.data.local.TodoEntity(
                id = nextId,
                title = title,
                description = description,
                isCompleted = false
            )
        )
    }

    override suspend fun updateTodo(item: TodoItem) {
        ensureLoaded()
        todoDao.update(item.toEntity())
    }

    override suspend fun deleteTodo(id: Int) {
        ensureLoaded()
        todoDao.deleteById(id)
    }
}
