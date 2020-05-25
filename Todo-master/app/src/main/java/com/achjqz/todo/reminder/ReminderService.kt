package com.achjqz.todo.reminder

import android.app.*
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.content.edit
import com.achjqz.todo.R
import com.achjqz.todo.ui.MainActivity
import com.achjqz.todo.utils.INTENT_TITLE
import com.achjqz.todo.utils.SHARED_REMIND_LIST
import org.koin.android.ext.android.get


class ReminderService : IntentService("ReminderService") {


    override fun onHandleIntent(intent: Intent?) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val id = "service"
            val channel =
                NotificationChannel(id, "reminder service", NotificationManager.IMPORTANCE_LOW)
            val manager =
                applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel)
            val notification = Notification.Builder(applicationContext, id).build()
            startForeground(1, notification)
        }
        //showing notification here
        val title = intent?.getStringExtra(INTENT_TITLE) ?: return
        val sharedPreferences = get<SharedPreferences>()
        val remindList =
            sharedPreferences.getStringSet(SHARED_REMIND_LIST, setOf())!!.toMutableSet()
        if (remindList.contains(title)) {
            sendNotification(title)
            remindList.remove(title)
            sharedPreferences.edit {
                putStringSet(SHARED_REMIND_LIST, remindList)
            }
        }
    }

    private fun sendNotification(title: String) {
        val manager =
            applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "task"

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.rocket)
            .setContentTitle(title)
            .setContentText("to do it!")
            .setAutoCancel(true)
            .setContentIntent(pendingIntent)


        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                channelId,
                "Reminder",
                NotificationManager.IMPORTANCE_HIGH
            )
            manager.createNotificationChannel(channel)
        }

        manager.notify(title.hashCode(), notificationBuilder.build())
    }
}