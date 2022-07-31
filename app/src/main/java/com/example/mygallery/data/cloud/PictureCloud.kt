package com.example.mygallery.data.cloud

import com.google.gson.annotations.SerializedName

data class PictureCloud(
    val id: Long,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    @SerializedName("download_url") val downloadUrl: String
)