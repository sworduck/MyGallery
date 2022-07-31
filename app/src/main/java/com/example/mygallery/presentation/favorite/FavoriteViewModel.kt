package com.example.mygallery.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.example.mygallery.data.cache.CacheDataSource
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel@Inject constructor(
    private val characterListFromCache: CacheDataSource) : ViewModel() {

    val pictureList = Pager(
        config = PagingConfig(
            pageSize = 10,
            enablePlaceholders = true,
            maxSize = 200
        )
    ) {
        characterListFromCache.getPictureCachePagingSource()
    }.flow.cachedIn(viewModelScope)


}