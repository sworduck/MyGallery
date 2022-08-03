package com.example.mygallery.data.cache

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.mygallery.data.Mapper
import com.example.mygallery.domain.Picture
import kotlin.math.max

class PictureCachePagingSource(private val pictureDao: PictureDao): PagingSource<Int, Picture>() {


    override fun getRefreshKey(state: PagingState<Int, Picture>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Picture> {
        // If params.key is null, it is the first load, so we start loading with STARTING_KEY
        val startKey = params.key ?: Companion.STARTING_KEY

        // We fetch as many articles as hinted to by params.loadSize
        val range = startKey.until(startKey + params.loadSize)

        //val pictureCache = service.fetchPhotos(startKey,10).map { PictureCloudToPictureDomainMapper().map(it) }
        val pictureListCache = pictureDao.getAllPicture(startKey,10).map { picture-> Mapper.pictureEntityToPicture(picture)}

        return LoadResult.Page(
            data = pictureListCache,
            prevKey = when (startKey) {
                STARTING_KEY -> null
                else -> when (val prevKey = ensureValidKey(key = range.first - params.loadSize)) {
                    // We're at the start, there's nothing more to load
                    STARTING_KEY -> null
                    else -> prevKey
                }
            },
            nextKey = range.last + 1
        )
    }
    private fun ensureValidKey(key: Int) = max(STARTING_KEY, key)

    companion object {
        private const val STARTING_KEY = 0
    }
}