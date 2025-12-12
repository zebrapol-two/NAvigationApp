package com.example.todolist.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository

class FakeTodoRepositoryForToggle : TodoRepository {
    private val todos = mutableListOf(
        TodoItem(1, "Купить молоко", "2 литра, обезжиренное", false),
        TodoItem(2, "Позвонить маме", "Спросить про выходные", true),
        TodoItem(3, "Сделать ДЗ по Android", "Clean Architecture + Compose", false)
    )

    override suspend fun getTodos(): List<TodoItem> = todos.toList()

    override suspend fun toggleTodo(id: Int) {
        val idx = todos.indexOfFirst { it.id == id }
        if (idx >= 0) {
            val old = todos[idx]
            todos[idx] = old.copy(isCompleted = !old.isCompleted)
        }
    }
}

class ToggleTodoUseCaseTest {

    @Test
    fun `ToggleTodoUseCase flips isCompleted`() = runBlocking {
        val repo = FakeTodoRepositoryForToggle()
        val get = GetTodosUseCase(repo)
        val toggle = ToggleTodoUseCase(repo)

        val before = get().first { it.id == 1 }.isCompleted
        toggle(1)
        val after = get().first { it.id == 1 }.isCompleted

        assertNotEquals(before, after)
        assertTrue(after)
    }
}

