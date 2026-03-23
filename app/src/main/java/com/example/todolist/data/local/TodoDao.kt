package com.example.todolist.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface TodoDao {
    @Query("SELECT * FROM todos ORDER BY id ASC")
    suspend fun getAll(): List<TodoEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(items: List<TodoEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: TodoEntity)

    @Update
    suspend fun update(item: TodoEntity)

    @Query("DELETE FROM todos WHERE id = :id")
    suspend fun deleteById(id: Int)

    @Query("SELECT COUNT(*) FROM todos")
    suspend fun count(): Int

    @Query("UPDATE todos SET isCompleted = :isCompleted WHERE id = :id")
    suspend fun setCompleted(id: Int, isCompleted: Boolean)

    @Query("SELECT MAX(id) FROM todos")
    suspend fun getMaxId(): Int?

    @Query("SELECT * FROM todos WHERE id = :id LIMIT 1")
    suspend fun getById(id: Int): TodoEntity?
}
