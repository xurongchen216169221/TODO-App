package com.achjqz.todo.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.achjqz.todo.R


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.content_container, TaskFragment())
            .commit()

    }
}
