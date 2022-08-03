package com.example.mygallery.data.cloud

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mygallery.data.Mapper
import com.example.mygallery.data.PictureRepository
import com.example.mygallery.domain.Picture


class PictureCloudPagingSource(private val service: ApiService) : PagingSource<Int, Picture>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Picture> {
        try {
            val page = params.key ?: 1
            val pictureCloud =
                service.fetchPictureList(page, 10).map { Mapper.pictureCloudToPicture(it) }
            pictureCloud.forEach {
                if (it.id.toInt() in PictureRepository.pictureListIdFromCache) {
                    it.favorite = true
                }
            }

            return LoadResult.Page(
                data = pictureCloud,
                prevKey = if (page - 1 < params.loadSize - 1 && page > 0) page - 1 else null,
                nextKey = if (page + 1 < params.loadSize - 1 && page > 0) page + 1 else null
            )
        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }


    override fun getRefreshKey(state: PagingState<Int, Picture>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }
}