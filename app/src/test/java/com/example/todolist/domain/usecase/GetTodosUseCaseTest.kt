package com.example.todolist.domain.usecase

import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Test
import com.example.todolist.domain.model.TodoItem
import com.example.todolist.domain.repository.TodoRepository

class FakeTodoRepositoryForGet : TodoRepository {
    private val todos = listOf(
        TodoItem(1, "Купить молоко", "2 литра, обезжиренное", false),
        TodoItem(2, "Позвонить маме", "Спросить про выходные", true),
        TodoItem(3, "Сделать ДЗ по Android", "Clean Architecture + Compose", false)
    )

    override suspend fun getTodos(): List<TodoItem> = todos
    override suspend fun toggleTodo(id: Int) {}
}

class GetTodosUseCaseTest {

    @Test
    fun `GetTodosUseCase returns 3 todos`() = runBlocking {
        val repo = FakeTodoRepositoryForGet()
        val useCase = GetTodosUseCase(repo)
        val result = useCase()
        assertEquals(3, result.size)
    }
}

