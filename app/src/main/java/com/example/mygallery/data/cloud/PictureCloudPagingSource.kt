package com.example.mygallery.data.cloud

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mygallery.data.Mapper
import com.example.mygallery.domain.Picture
import kotlin.math.max


class PictureCloudPagingSource(private val service: ApiService):PagingSource<Int,Picture>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Picture> {
        // If params.key is null, it is the first load, so we start loading with STARTING_KEY
        val startKey = params.key ?: Companion.STARTING_KEY

        // We fetch as many articles as hinted to by params.loadSize
        val range = startKey.until(startKey + params.loadSize)

        val pictureCloud = service.fetchPictureList(startKey,10).map { Mapper.pictureCloudToPicture(it) }

        return LoadResult.Page(
            data = pictureCloud,
            prevKey = when (startKey) {
                Companion.STARTING_KEY -> null
                else -> when (val prevKey = ensureValidKey(key = range.first - params.loadSize)) {
                    // We're at the start, there's nothing more to load
                    Companion.STARTING_KEY -> null
                    else -> prevKey
                }
            },
            nextKey = range.last + 1
        )
    }


    override fun getRefreshKey(state: PagingState<Int, Picture>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val article = state.closestItemToPosition(anchorPosition) ?: return null
        return ensureValidKey(key = article.id.toInt() - (state.config.pageSize / 2))
    }

    private fun ensureValidKey(key: Int) = max(Companion.STARTING_KEY, key)

    companion object {
        private const val STARTING_KEY = 0
    }
}