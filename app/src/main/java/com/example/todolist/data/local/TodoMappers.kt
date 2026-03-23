package com.example.todolist.data.local

import com.example.todolist.domain.model.TodoItem

fun TodoEntity.toDomain(): TodoItem = TodoItem(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted
)

fun TodoItem.toEntity(): TodoEntity = TodoEntity(
    id = id,
    title = title,
    description = description,
    isCompleted = isCompleted
)

