package com.example.todolist.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.presentation.viewmodel.TodoViewModel

@Composable
fun TodoDetailScreen(
    viewModel: TodoViewModel,
    todoId: Int,
    onBack: () -> Unit
) {
    val todosState = viewModel.todos.collectAsState()
    val existing = todosState.value.firstOrNull { it.id == todoId }
    val isNew = todoId < 0

    var title by rememberSaveable(todoId) { mutableStateOf(existing?.title ?: "") }
    var description by rememberSaveable(todoId) { mutableStateOf(existing?.description ?: "") }
    var isCompleted by rememberSaveable(todoId) { mutableStateOf(existing?.isCompleted ?: false) }

    LaunchedEffect(existing?.id) {
        if (existing != null) {
            title = existing.title
            description = existing.description
            isCompleted = existing.isCompleted
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        if (!isNew && existing == null) {
            Text("Задача не найдена", style = MaterialTheme.typography.titleMedium)
            Button(onClick = onBack, modifier = Modifier.padding(top = 24.dp)) {
                Text(text = "Назад")
            }
            return@Column
        }

        Text(
            text = if (isNew) "Новая задача" else "Редактирование",
            style = MaterialTheme.typography.titleLarge
        )

        OutlinedTextField(
            value = title,
            onValueChange = { title = it },
            label = { Text("Название") },
            modifier = Modifier.padding(top = 12.dp)
        )

        OutlinedTextField(
            value = description,
            onValueChange = { description = it },
            label = { Text("Описание") },
            modifier = Modifier.padding(top = 12.dp)
        )

        Row(modifier = Modifier.padding(top = 12.dp), verticalAlignment = Alignment.CenterVertically) {
            Checkbox(checked = isCompleted, onCheckedChange = { isCompleted = it })
            Spacer(modifier = Modifier.size(8.dp))
            Text(text = "Выполнено")
        }

        Row(modifier = Modifier.padding(top = 20.dp)) {
            Button(onClick = {
                if (isNew) {
                    viewModel.add(title, description)
                } else {
                    existing?.let { item ->
                        viewModel.update(item.copy(title = title, description = description, isCompleted = isCompleted))
                    }
                }
                onBack()
            }) {
                Text(text = "Сохранить")
            }

            Spacer(modifier = Modifier.size(12.dp))

            if (!isNew) {
                Button(onClick = {
                    existing?.let { viewModel.delete(it.id) }
                    onBack()
                }) {
                    Text(text = "Удалить")
                }
            }
        }

        Button(onClick = onBack, modifier = Modifier.padding(top = 16.dp)) {
            Text(text = "Назад")
        }
    }
}
