package com.example.mygallery.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.*
import com.example.mygallery.data.Mapper
import com.example.mygallery.data.PictureRepository
import com.example.mygallery.domain.Picture
import com.example.mygallery.domain.Status
import com.example.mygallery.presentation.adapter.FragmentAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
): ViewModel() {

    private var _status:MutableLiveData<Status> = MutableLiveData<Status>()
    val status:LiveData<Status> = _status

    private val pictureList: Flow<PagingData<Picture>> =
        Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = true,
                maxSize = 50,
                initialLoadSize = 1
            )
        ) {
            pictureRepository.getPictureCloudPagingSource()
        }.flow

    fun bindPaging(adapter: FragmentAdapter) {
        checkLoadState(adapter)
        viewModelScope.launch {
            pictureList
                .cachedIn(viewModelScope)
                .collectLatest {
                    adapter.submitData(it)
                }
        }
    }

    private fun checkLoadState(adapter: FragmentAdapter){
        viewModelScope.launch {
            adapter.loadStateFlow.collectLatest { loadState->
                when(val currentState = loadState.refresh){
                    is LoadState.Loading ->{
                        _status.value = Status.Success()
                    }
                    is LoadState.Error ->{
                        _status.value = Status.Fail()
                    }
                }
            }
        }
    }

    fun addPicture(picture: Picture){
        pictureRepository.savePicture(Mapper.pictureToPictureEntity(picture))
    }

    fun removePicture(picture: Picture) {
        pictureRepository.removePicture(Mapper.pictureToPictureEntity(picture))
    }

    fun onClickRetryButton(adapter: FragmentAdapter) {
        bindPaging(adapter)
    }
}