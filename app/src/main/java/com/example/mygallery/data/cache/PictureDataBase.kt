package com.example.mygallery.data.cache

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [PictureEntity::class],
    version = 4,
    exportSchema = false)
abstract class PictureDataBase : RoomDatabase() {

    companion object {
        const val DATABASE_NAME = "picture_db"
    }

    abstract fun pictureDatabaseDao(): PictureDao
}