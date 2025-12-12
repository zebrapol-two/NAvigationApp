package com.example.todolist.presentation.ui.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.domain.model.TodoItem

@Composable
fun TodoRow(item: TodoItem, onToggle: (Int) -> Unit, onClick: (Int) -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(8.dp))
            .background(Color(0xFFF0F0F0))
            .clickable { onClick(item.id) }
            .padding(12.dp)
            .testTag("todo_row_${item.id}"),
        horizontalArrangement = Arrangement.Start
    ) {
        Checkbox(
            modifier = Modifier.testTag("todo_checkbox_${item.id}"),
            checked = item.isCompleted,
            onCheckedChange = { onToggle(item.id) },
            colors = CheckboxDefaults.colors(
                checkedColor = Color(0xFFFF9800),
                uncheckedColor = Color(0xFFBDBDBD),
                checkmarkColor = Color.White
            )
        )
        Spacer(modifier = Modifier.size(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(text = item.title, style = MaterialTheme.typography.titleMedium)
            if (item.description.isNotEmpty()) {
                Text(text = item.description, style = MaterialTheme.typography.bodySmall)
            }
        }

        val statusText = if (item.isCompleted) "Выполнено" else "Не выполнено"
        val statusColor = if (item.isCompleted) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.error
        Text(
            text = statusText,
            color = statusColor,
            fontSize = 12.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 8.dp)
        )
    }
}
