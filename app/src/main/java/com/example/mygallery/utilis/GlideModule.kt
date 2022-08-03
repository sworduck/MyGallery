package com.example.mygallery.utilis

import android.content.Context
import android.graphics.Color
import android.graphics.ColorMatrix
import android.graphics.ColorMatrixColorFilter
import androidx.swiperefreshlayout.widget.CircularProgressDrawable
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule

@GlideModule
class GlideModule : AppGlideModule() {

    object GlideProgressBar {

        fun getCircularProgressDrawable(context: Context) =
            CircularProgressDrawable(context).apply {
                setColorSchemeColors(0xFFFFFFFF.toInt())
                strokeWidth = 15f
                centerRadius = 50f
                start()
            }

    }

}