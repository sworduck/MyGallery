package com.example.mygallery.data

import com.example.mygallery.data.cache.CacheDataSource
import com.example.mygallery.data.cache.PictureCachePagingSource
import com.example.mygallery.data.cache.PictureEntity
import com.example.mygallery.data.cloud.CloudDataSource
import com.example.mygallery.data.cloud.PictureCloudPagingSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class PictureRepository @Inject constructor(
    private val pictureCloudDataSource: CloudDataSource,
    private val pictureCacheDataSource: CacheDataSource,
) {

    companion object {
        val pictureListIdFromCache: ArrayList<Int> = arrayListOf()
    }

    fun getPictureCachePagingSource(): PictureCachePagingSource {
        return pictureCacheDataSource.getPictureCachePagingSource()
    }

    fun getPictureCloudPagingSource(): PictureCloudPagingSource {
        CoroutineScope(Dispatchers.IO).launch {
            pictureListIdFromCache.addAll(pictureCacheDataSource.fetchPictureList()
                .map { it.id.toInt() })
        }
        return pictureCloudDataSource.getPictureCloudPagingSource()
    }

    fun savePicture(picture: PictureEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            pictureCacheDataSource.savePicture(picture)
            pictureListIdFromCache.add(picture.id.toInt())
        }
    }

    fun removePicture(picture: PictureEntity) {
        CoroutineScope(Dispatchers.IO).launch {
            pictureCacheDataSource.removePicture(picture)
            pictureListIdFromCache.remove(picture.id.toInt())
        }
    }
}