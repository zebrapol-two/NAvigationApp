package com.example.todolist.data.local

import android.content.Context
import org.json.JSONArray

class TodoJsonDataSource(private val context: Context) {
    fun getTodos(): List<com.example.todolist.data.model.TodoItemDto> {
        val json = context.assets.open("todos.json").bufferedReader().use { it.readText() }
        val arr = JSONArray(json)
        val list = mutableListOf<com.example.todolist.data.model.TodoItemDto>()
        for (i in 0 until arr.length()) {
            val obj = arr.getJSONObject(i)
            list.add(
                com.example.todolist.data.model.TodoItemDto(
                    id = obj.getInt("id"),
                    title = obj.getString("title"),
                    description = obj.getString("description"),
                    isCompleted = obj.getBoolean("isCompleted")
                )
            )
        }
        return list
    }
}
