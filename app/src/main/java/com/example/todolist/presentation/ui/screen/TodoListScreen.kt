package com.example.todolist.presentation.ui.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.todolist.presentation.viewmodel.TodoViewModel
import com.example.todolist.presentation.ui.component.TodoRow

@Composable
fun TodoListScreen(viewModel: TodoViewModel, onItemClick: (Int) -> Unit) {
    val todosState = viewModel.todos.collectAsState()
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(horizontal = 16.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items(todosState.value) { item ->
            TodoRow(item = item, onToggle = { viewModel.toggle(it) }, onClick = { onItemClick(it) })
        }
    }
}
