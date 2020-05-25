package com.achjqz.todo

import android.app.Application
import com.achjqz.todo.utils.appModule
import com.achjqz.todo.utils.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class TaskApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger()
            androidContext(this@TaskApplication)
            modules(arrayListOf(appModule, viewModelModule))
        }
    }

}