package com.achjqz.todo.utils

import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions


@BindingAdapter(value = ["imageUrl", "circle", "corners"], requireAll = false)
fun bindImageFromUrl(view: ImageView, imageUrl: String?, circle: Boolean, corners: Int) {
    if (!imageUrl.isNullOrEmpty()) {
        val glide = Glide.with(view.context)
            .load(imageUrl)
            .centerCrop()

        if (circle) {
            glide.circleCrop().into(view)
        } else if (corners != 0) {
            glide.apply(RequestOptions().transform(RoundedCorners(corners))).into(view)
        } else {
            glide.into(view)
        }

    }
}

