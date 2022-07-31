package com.example.mygallery.data

import com.example.mygallery.data.cache.CacheDataSource
import com.example.mygallery.data.cache.PictureEntity
import com.example.mygallery.data.cloud.CloudDataSource
import com.example.mygallery.domain.Picture
import kotlinx.coroutines.flow.Flow

class PictureRepository(private val pictureCloudDataSource: CloudDataSource,
                        private val pictureCacheDataSource: CacheDataSource,) {
    /*
    suspend fun fetchPhotoList(page:Long,limit:Int):List<Picture>{
        return apiService.fetchPhotos(page,limit).map { pictureCloudToPictureDomain.map(it) }
    }

     */
    suspend fun fetchPictureList(page:Int,limit:Int): Flow<List<Picture>>{
        return pictureCloudDataSource.fetchPhotoList(page,limit)
    }

    fun savePictureList(picture:Picture){
        pictureCacheDataSource.savePicture(Mapper.pictureToPictureEntity(picture))
    }

    suspend fun fetchFavoritePictureList(page:Int,limit: Int):List<PictureEntity>{
        return pictureCacheDataSource.fetchPictureList()
    }
}