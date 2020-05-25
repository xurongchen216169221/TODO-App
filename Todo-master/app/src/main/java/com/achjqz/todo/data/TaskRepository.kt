package com.achjqz.todo.data

import androidx.lifecycle.LiveData

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class TaskRepository(
    private val taskDao: TaskDao
) {


    fun getAllTasks(): LiveData<List<Task>> = taskDao.getAllTasks()


    suspend fun deleteAll() {
        withContext(Dispatchers.IO) {
            taskDao.deleteAllTask()
        }
    }

    suspend fun deleteTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.deleteTask(task)
        }
    }

    suspend fun addTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.addTask(task)
        }
    }

    suspend fun updateTask(task: Task) {
        withContext(Dispatchers.IO) {
            taskDao.update(task)
        }
    }

}