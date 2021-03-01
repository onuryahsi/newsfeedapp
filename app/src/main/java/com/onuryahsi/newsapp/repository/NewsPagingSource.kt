package com.onuryahsi.newsapp.repository

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.onuryahsi.newsapp.model.Article
import com.onuryahsi.newsapp.service.NewsRestAPI
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

private const val SERVICE_STARTING_PAGE_INDEX = 1

class NewsPagingSource @Inject constructor(private val api: NewsRestAPI, private val query: String) :
    PagingSource<Int, Article>() {
    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Article> {
        val position = params.key ?: SERVICE_STARTING_PAGE_INDEX
        return try {
            val response =
                api.getArticles(q = query, page = position, apiKey = NewsRestAPI.api_key).body()
            val result = response?.articles
            val nextKey = if (result?.isEmpty()!!) {
                null
            } else {
                if (position < 5) position + 1 else null
            }

            LoadResult.Page(
                data = result,
                prevKey = if (position == SERVICE_STARTING_PAGE_INDEX) null else position - 1,
                nextKey = nextKey
            )
        } catch (e1: IOException) {
            println("Exception: - $e1")
            LoadResult.Error(e1)
        } catch (e2: HttpException) {
            println("Exception: - $e2")
            LoadResult.Error(e2)
        } catch (e3: Exception) {
            println("Exception: - $e3")
            LoadResult.Error(e3)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Article>): Int? {
        return state.anchorPosition
    }

}