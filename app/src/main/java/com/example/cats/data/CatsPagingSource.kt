package com.example.cats.data

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.cats.api.CatsApi
import com.example.cats.model.Cat
import retrofit2.HttpException
import java.io.IOException

private const val START_PAGE_INDEX = 1

class CatsPagingSource(
    private val catsApi: CatsApi
): PagingSource<Int, Cat>() {

    override fun getRefreshKey(state: PagingState<Int, Cat>): Int? {
        val anchorPosition = state.anchorPosition ?: return null
        val page = state.closestPageToPosition(anchorPosition) ?: return null
        return page.prevKey?.plus(1) ?: page.nextKey?.minus(1)
//        return state.anchorPosition?.let { state.closestItemToPosition(it)?.id?.toInt() }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> {
        val position = params.key ?: START_PAGE_INDEX
        return try {
            val response = catsApi.getListOfCats(params.loadSize, position)
            Log.i(LOG_TAG, "Retrofit query invoked!")
            LoadResult.Page(
                response,
                if (position == START_PAGE_INDEX) null else position - 1,
                if (response.isEmpty()) null else position + 1
            )
        } catch (ex: IOException) {
            LoadResult.Error(ex)
        } catch (ex: HttpException) {
            LoadResult.Error(ex)
        }
    }

    companion object {
        private const val LOG_TAG = "myTag"
    }
}