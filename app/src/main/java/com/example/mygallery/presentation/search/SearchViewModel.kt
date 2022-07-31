package com.example.mygallery.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.mygallery.data.cache.CacheDataSource
import com.example.mygallery.data.cache.PictureEntity
import com.example.mygallery.data.cloud.ApiService
import com.example.mygallery.data.cloud.CloudDataSource
import com.example.mygallery.data.cloud.PictureCloudPagingSource
import com.example.mygallery.domain.Picture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val characterListFromCloud: CloudDataSource,
    private val characterListFromCache: CacheDataSource,
): ViewModel() {
    val pictureList:Flow<PagingData<Picture>> = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        characterListFromCloud.getPictureCloudPagingSource()
    }.flow.cachedIn(viewModelScope)

}