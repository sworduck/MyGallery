package com.example.mygallery.data.cache

import com.example.mygallery.data.Mapper
import com.example.mygallery.data.cloud.PictureCloudPagingSource
import com.example.mygallery.domain.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn


class CacheDataSource(private val pictureDao: PictureDao) {
    fun fetchPictureList(page:Int,limit:Int): Flow<List<Picture>> {
        return flow {
            emit( pictureDao.getAllPicture(limit,10).map { Mapper.pictureEntityToPicture(it) })
        }.flowOn(Dispatchers.IO)
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