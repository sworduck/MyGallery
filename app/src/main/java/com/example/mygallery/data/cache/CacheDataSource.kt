package com.example.mygallery.data.cache

import com.example.mygallery.data.Mapper
import com.example.mygallery.domain.Picture

class CacheDataSource(private val pictureDao: PictureDao) {

    suspend fun fetchPictureList(): List<Picture> {
        return pictureDao.getAllPicture().map { Mapper.pictureEntityToPicture(it) }
    }

    fun savePicture(picture: PictureEntity) {
        pictureDao.insert(picture)
    }

    fun removePicture(picture: PictureEntity) {
        pictureDao.delete(picture.id.toInt())
    }

    fun getPictureCachePagingSource(): PictureCachePagingSource {
        return PictureCachePagingSource(pictureDao)
    }
}