package com.example.mygallery.utilis

import android.widget.ImageView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadImage(url: String) {
    val circularProgressDrawable = GlideModule.GlideProgressBar.getCircularProgressDrawable(this.context)
    Glide.with(this)
        .load(url)
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .placeholder(circularProgressDrawable)
        .into(this)
}