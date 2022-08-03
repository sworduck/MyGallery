package com.example.mygallery.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.mygallery.data.Mapper
import com.example.mygallery.data.cache.CacheDataSource
import com.example.mygallery.data.cloud.ApiService
import com.example.mygallery.data.cloud.CloudDataSource
import com.example.mygallery.data.cloud.PictureCloudPagingSource
import com.example.mygallery.domain.Picture
import com.example.mygallery.presentation.adapter.FragmentAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val characterListFromCloud: CloudDataSource,
    private val characterListFromCache: CacheDataSource,
): ViewModel() {

    private val pictureList: Flow<PagingData<Picture>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) {
            characterListFromCloud.getPictureCloudPagingSource()
        }.flow

    fun bindPaging(adapter: FragmentAdapter) {
        viewModelScope.launch {
            pictureList
                .cachedIn(viewModelScope)
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }

    fun addPicture(picture: Picture){
        CoroutineScope(Dispatchers.IO).launch {
            characterListFromCache.savePicture(Mapper.pictureToPictureEntity(picture))
        }
    }

    fun removePicture(picture: Picture) {
        CoroutineScope(Dispatchers.IO).launch {
            characterListFromCache.removePicture(Mapper.pictureToPictureEntity(picture))
        }
    }
}