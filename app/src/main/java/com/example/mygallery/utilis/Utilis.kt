package com.example.mygallery.utilis

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun ImageView.loadImage(url: String) {
    val circularProgressDrawable = GlideModule.GlideProgressBar.getCircularProgressDrawable(this.context)
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(circularProgressDrawable)
        .into(this)
}