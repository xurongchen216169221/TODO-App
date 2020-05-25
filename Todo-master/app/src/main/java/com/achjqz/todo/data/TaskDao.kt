package com.achjqz.todo.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface TaskDao {
    @Query("SELECT * From tasks ORDER BY priority DESC")
    fun getAllTasks(): LiveData<List<Task>>

    @Query("DELETE From tasks")
    fun deleteAllTask()

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun addTask(task: Task)

    @Update
    fun update(task: Task)
    @Delete
    fun deleteTask(task: Task)
}