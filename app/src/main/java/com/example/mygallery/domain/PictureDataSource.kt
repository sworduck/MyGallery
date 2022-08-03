package com.example.mygallery.domain

import com.example.mygallery.data.PictureRepository
import com.example.mygallery.data.cache.PictureCachePagingSource
import com.example.mygallery.data.cloud.PictureCloudPagingSource
import kotlinx.coroutines.flow.Flow
import java.lang.Exception
import javax.inject.Inject


class PictureDataSource @Inject constructor(private val pictureRepository: PictureRepository) {
    suspend fun getPictureNetwork(page:Int, limit:Int): Flow<List<Picture>> {
        try {
            val pictureList = pictureRepository.fetchPictureList(page,limit)
        }
        catch (e:Exception){

        }

        return pictureRepository.fetchPictureList(page,limit)
    }

    fun getPictureLocal(): Flow<List<Picture>> {
        return pictureRepository.fetchFavoritePictureList()
    }

    fun savePicture(picture: Picture){

    }

    fun removePicture(picture: Picture){

    }

    fun getPictureCachePagingSource(): PictureCachePagingSource {
        return pictureRepository.getPictureCachePagingSource()
    }

    fun getPictureCloudPagingSource(): PictureCloudPagingSource {
        return pictureRepository.getPictureCloudPagingSource()
    }


}