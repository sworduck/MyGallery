package com.example.mygallery.data.cloud

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    companion object{
        const val BASE_URL = "https://picsum.photos/"
    }

    @GET("/v2/list")
    suspend fun fetchPictureList(
        @Query("page") page: Int,
        @Query("limit") limit: Int
    ): List<PictureCloud>

    @GET("/id/{photoId}/info")
    suspend fun fetchPhotoById(@Path("photoId") photoId: Long): PictureCloud
}