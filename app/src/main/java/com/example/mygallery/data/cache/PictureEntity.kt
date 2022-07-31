package com.example.mygallery.data.cache

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class PictureEntity(
    val id: Long,
    val author: String,
    val width: Int,
    val height: Int,
    val url: String,
    val downloadUrl: String,
){
    @PrimaryKey(autoGenerate = true)var uid:Int = 0
}