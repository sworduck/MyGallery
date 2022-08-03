package com.example.mygallery.domain

data class Picture(
    val id: Long,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    var favorite:Boolean,
    val downloadUrl: String
)