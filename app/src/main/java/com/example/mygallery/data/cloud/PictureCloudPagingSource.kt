package com.example.mygallery.data.cloud

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mygallery.data.Mapper
import com.example.mygallery.data.PictureRepository
import com.example.mygallery.domain.Picture
import java.lang.Integer.max

class PictureCloudPagingSource(private val service: ApiService) : PagingSource<Int, Picture>() {

    companion object {
        private const val STARTING_KEY = 1
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Picture> {
        // If params.key is null, it is the first load, so we start loading with STARTING_KEY
        try {
            val startKey = params.key ?: STARTING_KEY

            // We fetch as many articles as hinted to by params.loadSize
            val range = startKey.until(startKey + params.loadSize)

            val pictureCloud =
                service.fetchPictureList(startKey, 10).map { Mapper.pictureCloudToPicture(it) }
            pictureCloud.forEach {
                if(it.id.toInt() in PictureRepository.pictureListIdFromCache) {
                    it.favorite = true
                }
            }
            return LoadResult.Page(
                data = pictureCloud,
                prevKey = when (startKey) {
                    STARTING_KEY -> null
                    else -> when (val prevKey =
                        ensureValidKey(key = range.first - params.loadSize)) {
                        // We're at the start, there's nothing more to load
                        STARTING_KEY -> null
                        else -> prevKey
                    }
                },
                nextKey = range.last + 10
            )
        }catch (e:Exception){
            return LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Picture>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(10)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(10)
        }
    }

    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)
}