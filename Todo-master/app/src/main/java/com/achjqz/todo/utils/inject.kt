package com.achjqz.todo.utils

import android.content.Context
import androidx.room.Room
import com.achjqz.todo.data.AppDatabase
import com.achjqz.todo.data.TaskRepository
import com.achjqz.todo.viewmodels.TaskListViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val appModule = module {
    single {
        Room.databaseBuilder(androidContext(), AppDatabase::class.java, "task_database")
            .fallbackToDestructiveMigration()
            .build()
    }
    single {
        get<AppDatabase>().taskDao()
    }

    single {
        androidContext().getSharedPreferences(TASK_SHARED, Context.MODE_PRIVATE)
    }
}

val viewModelModule = module {
    single { TaskRepository(get()) }

    viewModel {
        TaskListViewModel(get())
    }
}