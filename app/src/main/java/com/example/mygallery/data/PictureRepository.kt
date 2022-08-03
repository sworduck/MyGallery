package com.example.mygallery.data

import com.example.mygallery.data.cache.CacheDataSource
import com.example.mygallery.data.cache.PictureCachePagingSource
import com.example.mygallery.data.cache.PictureEntity
import com.example.mygallery.data.cloud.CloudDataSource
import com.example.mygallery.data.cloud.PictureCloudPagingSource
import com.example.mygallery.domain.Picture
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

class PictureRepository @Inject constructor (private val pictureCloudDataSource: CloudDataSource,
                        private val pictureCacheDataSource: CacheDataSource,) {

    suspend fun fetchPictureList(page:Int,limit:Int): Flow<List<Picture>>{
        return pictureCloudDataSource.fetchPhotoList(page,limit)
    }

    fun savePictureList(picture:Picture){
        pictureCacheDataSource.savePicture(Mapper.pictureToPictureEntity(picture))
    }

    fun fetchFavoritePictureList(): Flow<List<Picture>>{
        return pictureCacheDataSource.fetchPictureList()
    }

    fun getPictureCachePagingSource(): PictureCachePagingSource {
        return pictureCacheDataSource.getPictureCachePagingSource()
    }

    fun getPictureCloudPagingSource(): PictureCloudPagingSource {
        return pictureCloudDataSource.getPictureCloudPagingSource()
    }

    fun savePicture(picture:PictureEntity){
        CoroutineScope(Dispatchers.IO).launch {
        pictureCacheDataSource.savePicture(picture)
        }
    }

    fun removePicture(picture: PictureEntity){
        CoroutineScope(Dispatchers.IO).launch {
            pictureCacheDataSource.removePicture(picture)
        }
    }
}