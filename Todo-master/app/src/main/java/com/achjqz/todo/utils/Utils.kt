package com.achjqz.todo.utils

import android.content.Context
import android.util.Log
import android.widget.Toast
import java.util.*

fun Any.loge(info: String) {
    Log.e(this.toString(), info)
}

fun Context.showToast(msg: String) {
    val toast = Toast.makeText(this, "", Toast.LENGTH_SHORT)
    toast.setText(msg)
    toast.show()
}

fun getCurrentTime(): Long = System.currentTimeMillis() / 1000

val randomImg: String
    get() {
        val imgs = arrayOf(
            "https://cdn.dribbble.com/users/702789/screenshots/4561577/preview.png",
            "https://cdn.dribbble.com/users/702789/screenshots/4292692/800.png",
            "https://cdn.dribbble.com/users/1780890/screenshots/5036664/artboard_2x.png",
            "https://cdn.dribbble.com/users/79571/screenshots/5282399/family_time.png",
            "https://cdn.dribbble.com/users/702789/screenshots/4672014/memory.png",
            "https://cdn.dribbble.com/users/702789/screenshots/4686314/puzzle.png",
            "https://cdn.dribbble.com/users/702789/screenshots/4658644/artboard.png",
            "https://cdn.dribbble.com/users/702789/screenshots/3966519/800.png",
            "https://cdn.dribbble.com/users/25514/screenshots/4255585/vyta_brand_illustration_ramotion.gif",
            "https://cdn.dribbble.com/users/702789/screenshots/4498099/800x600.png",
            "https://cdn.dribbble.com/users/702789/screenshots/4424797/preview.png",
            "https://cdn.dribbble.com/users/702789/screenshots/4649801/time-final.gif"
        )
        val random = Random()
        val num = random.nextInt(imgs.size)
        return imgs[num]
    }