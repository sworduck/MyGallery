package com.example.mygallery.data.cache

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface PictureDao {
    @Query("SELECT * FROM pictureEntity ORDER BY id ASC LIMIT :limit OFFSET :offset")
    suspend fun getAllPicture(limit:Int,offset:Int): List<PictureEntity>

    @Insert
    fun insert(vararg pictureEntity: PictureEntity)

    @Query("DELETE FROM pictureEntity WHERE id = :id")
    fun delete(id:Int)

    @Query("DELETE FROM pictureEntity")
    fun deleteAll()
}