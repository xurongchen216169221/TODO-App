package com.achjqz.todo.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.achjqz.todo.data.Task
import com.achjqz.todo.data.TaskRepository
import kotlinx.coroutines.launch

class TaskListViewModel(
    private val taskRepository: TaskRepository
) : ViewModel() {


    val tasks = taskRepository.getAllTasks()

    fun addTask(task: Task) = viewModelScope.launch {
        taskRepository.addTask(task)
    }

    fun updateTask(task: Task) = viewModelScope.launch {
        taskRepository.updateTask(task)
    }

    fun deleteTask(task: Task) = viewModelScope.launch {
        taskRepository.deleteTask(task)
    }

    fun deleteAll() = viewModelScope.launch {
        taskRepository.deleteAll()
    }

}