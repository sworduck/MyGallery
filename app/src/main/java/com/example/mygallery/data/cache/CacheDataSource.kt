package com.example.mygallery.data.cache

import com.example.mygallery.data.cloud.PictureCloudPagingSource
import com.example.mygallery.domain.Picture
import kotlinx.coroutines.flow.Flow


class CacheDataSource(private val pictureDao: PictureDao) {
    fun fetchPictureList(): List<PictureEntity> {
        return pictureDao.getAllPicture()
    }

    fun savePicture(picture:PictureEntity){
        pictureDao.insert(picture)
    }

    fun getPictureCachePagingSource(): PictureCachePagingSource {
        return PictureCachePagingSource(pictureDao)
    }
}