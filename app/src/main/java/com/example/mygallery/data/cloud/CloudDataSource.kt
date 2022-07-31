package com.example.mygallery.data.cloud

import com.example.mygallery.data.Mapper
import com.example.mygallery.domain.Picture
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class CloudDataSource @Inject constructor(private val apiService: ApiService) {
    suspend fun fetchPhotoList(page:Int,limit:Int): Flow<List<Picture>> {
        return flow{
            emit(apiService.fetchPictureList(page,limit).map { Mapper.pictureCloudToPicture(it)})
        }.flowOn(Dispatchers.IO)
    }

    fun getPictureCloudPagingSource():PictureCloudPagingSource{
        return PictureCloudPagingSource(apiService)
    }
}