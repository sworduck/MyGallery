package com.example.mygallery.data.cache

import com.example.mygallery.data.Mapper
import com.example.mygallery.data.cloud.PictureCloudPagingSource
import com.example.mygallery.domain.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class CacheDataSource(private val pictureDao: PictureDao) {
    suspend fun fetchPictureList(): List<Picture> {
        return pictureDao.getAllPicture().map { Mapper.pictureEntityToPicture(it) }
    }

    fun savePicture(picture:PictureEntity){
        pictureDao.insert(picture)
    }

    fun removePicture(picture: PictureEntity){
        pictureDao.delete(picture.id.toInt())
    }

    fun getPictureCachePagingSource(): PictureCachePagingSource {
        return PictureCachePagingSource(pictureDao)
    }
}