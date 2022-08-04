package com.example.mygallery.data.cloud

import javax.inject.Inject

class CloudDataSource @Inject constructor(private val apiService: ApiService) {
    fun getPictureCloudPagingSource(): PictureCloudPagingSource {
        return PictureCloudPagingSource(apiService)
    }
}