package com.achjqz.todo.ui

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.edit
import com.achjqz.todo.R
import com.achjqz.todo.utils.SHARED_FIRST
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_welcome.*
import org.koin.android.ext.android.get

class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val sharedPreferences = get<SharedPreferences>()
        val isFirst = sharedPreferences.getBoolean(SHARED_FIRST, true)
        if (isFirst) {
            setContentView(R.layout.activity_welcome)
            Glide.with(this).load(R.drawable.welcome_background).into(intro_icon)
            start.setOnClickListener {
                startActivity(Intent(this, MainActivity::class.java))
            }
            sharedPreferences.edit {
                putBoolean(SHARED_FIRST, false)
            }
        } else {
            startActivity(Intent(this, MainActivity::class.java).apply {
                addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION)
            })
        }
    }
}