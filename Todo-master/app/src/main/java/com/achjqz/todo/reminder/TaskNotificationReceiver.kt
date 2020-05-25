package com.achjqz.todo.reminder

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import com.achjqz.todo.utils.INTENT_TITLE

class TaskNotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        val i = Intent(context, ReminderService::class.java)
        i.putExtra(INTENT_TITLE, intent.getStringExtra(INTENT_TITLE))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(i)
        } else {
            context.startService(i)
        }
    }
}