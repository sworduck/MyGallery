package com.example.mygallery.presentation.favorite

import androidx.lifecycle.ViewModel
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.mygallery.data.Mapper
import com.example.mygallery.data.PictureRepository
import com.example.mygallery.domain.Picture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
) : ViewModel() {

    private val _removeItemFlow = MutableStateFlow(mutableListOf<Picture>())
    val removedItemsFlow: Flow<List<Picture>> = _removeItemFlow

    val pictureList: Flow<PagingData<Picture>> =
        Pager(
            config = PagingConfig(
                pageSize = 1,
                enablePlaceholders = true,
                maxSize = 20,
                initialLoadSize = 1
            )
        ) {
            pictureRepository.getPictureCachePagingSource()
        }.flow

    fun remove(item: Picture?) {
        if (item == null) {
            return
        }

        val removes = _removeItemFlow.value
        val list = mutableListOf(item)
        list.addAll(removes)
        _removeItemFlow.value = list

        removePicture(item)
    }

    private fun removePicture(picture: Picture) {
        pictureRepository.removePicture(Mapper.pictureToPictureEntity(picture))
    }
}