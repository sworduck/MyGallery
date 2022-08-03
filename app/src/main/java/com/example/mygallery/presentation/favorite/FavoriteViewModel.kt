package com.example.mygallery.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.mygallery.data.cache.CacheDataSource
import com.example.mygallery.domain.Picture
import com.example.mygallery.presentation.adapter.FragmentAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val characterListFromCache: CacheDataSource,
) : ViewModel() {

    private val pictureList: Flow<PagingData<Picture>> =
        Pager(
            config = PagingConfig(
                pageSize = 10,
                enablePlaceholders = true,
                maxSize = 200
            )
        ) {
            characterListFromCache.getPictureCachePagingSource()
        }.flow

    private val _removeItemFlow = MutableStateFlow(mutableListOf<Picture>())
    private val removedItemsFlow: Flow<MutableList<Picture>> get() = _removeItemFlow

    fun bindPaging(adapter: FragmentAdapter) {
        viewModelScope.launch {
            pictureList
                .cachedIn(viewModelScope)
                .combine(removedItemsFlow) { pagingData, removed ->
                    pagingData.filter {
                        it !in removed
                    }
                }
                .collectLatest {
                    adapter.submitData(it)
                    println(it)
                }
        }
    }

    fun remove(item: Picture?) {
        if (item == null) {
            return
        }

        val removes = _removeItemFlow.value
        val list = mutableListOf(item)
        list.addAll(removes)
        _removeItemFlow.value = list
    }

}