package com.example.mygallery.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {
    @Query("SELECT * FROM pictureEntity")
    fun getAllPicture(): List<PictureEntity>

    @Insert
    fun insert(vararg pictureEntity: PictureEntity)

    @Delete
    fun delete(pictureEntity:PictureEntity)

    @Query("DELETE FROM pictureEntity")
    fun deleteAll()
}