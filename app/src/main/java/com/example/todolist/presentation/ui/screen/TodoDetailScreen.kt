package com.example.todolist.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todolist.domain.model.TodoItem

@Composable
fun TodoDetailScreen(item: TodoItem?, onBack: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        if (item == null) {
            Text("Задача не найдена", style = MaterialTheme.typography.titleMedium)
        } else {
            Text(text = item.title, style = MaterialTheme.typography.titleLarge)
            Text(text = item.description, style = MaterialTheme.typography.bodyMedium, modifier = Modifier.padding(top = 8.dp))
            val statusText = if (item.isCompleted) "Выполнено" else "Не выполнено"
            val statusColor = if (item.isCompleted) Color(0xFF4CAF50) else MaterialTheme.colorScheme.error
            Text(
                text = "Статус: $statusText",
                style = MaterialTheme.typography.bodySmall,
                color = statusColor,
                modifier = Modifier.padding(top = 8.dp)
            )
        }

        Button(onClick = onBack, modifier = Modifier.padding(top = 24.dp)) {
            Text(text = "Назад")
        }
    }
}
