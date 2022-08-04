package com.example.mygallery.presentation.search

import androidx.lifecycle.ViewModel
import androidx.paging.*
import com.example.mygallery.data.Mapper
import com.example.mygallery.data.PictureRepository
import com.example.mygallery.domain.Picture
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val pictureRepository: PictureRepository,
) : ViewModel() {

    val pictureList: Flow<PagingData<Picture>> =
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

    fun addPicture(picture: Picture) {
        pictureRepository.savePicture(Mapper.pictureToPictureEntity(picture))
    }

    fun removePicture(picture: Picture) {
        pictureRepository.removePicture(Mapper.pictureToPictureEntity(picture))
    }
}