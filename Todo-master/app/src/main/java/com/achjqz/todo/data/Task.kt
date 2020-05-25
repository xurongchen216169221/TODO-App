package com.achjqz.todo.data

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey


@Entity(tableName = "tasks", indices = [Index(value = ["title"], unique = true)])
data class Task(
    val title: String,
    val imgUrl: String,
    val remark: String,
    val subtitle: String,
    val priority: Int,
    val timestamp: Long
) {
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0
}