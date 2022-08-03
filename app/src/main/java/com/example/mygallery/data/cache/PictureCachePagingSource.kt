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
        val pictureListCache = pictureDao.getAllPicture().map { picture-> Mapper.pictureEntityToPicture(picture)}
        val page = params.key?: 1
        return LoadResult.Page(
            data = pictureListCache,
            prevKey = if(page-1<params.loadSize-1&&page>0) page-1 else null,
            nextKey = if(page+1<params.loadSize-1&&page>0) page+1 else null
        )
    }
}