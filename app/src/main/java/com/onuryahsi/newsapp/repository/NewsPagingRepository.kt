package com.onuryahsi.newsapp.repository

import androidx.lifecycle.LiveData
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.liveData
import com.onuryahsi.newsapp.model.Article
import com.onuryahsi.newsapp.service.NewsRestAPI
import javax.inject.Inject

class NewsPagingRepository @Inject constructor(private val api: NewsRestAPI) {
    fun getNews(query: String): LiveData<PagingData<Article>> {
        return Pager(
            config = PagingConfig(
                pageSize = 5,
                enablePlaceholders = false
            ),
            pagingSourceFactory = {
                NewsPagingSource(api, query)
            }
        ).liveData
    }
}