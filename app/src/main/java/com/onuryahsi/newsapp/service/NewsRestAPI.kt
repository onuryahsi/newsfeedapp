package com.onuryahsi.newsapp.service

import com.onuryahsi.newsapp.model.ArticleResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsRestAPI {

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
        const val api_key = "b5c009bdd1e142739b7de32185129914"
    }

    @GET("everything")
    suspend fun getArticles(
        @Query("q") q: String,
        @Query("page") page: Int,
        @Query("apiKey") apiKey: String
    ): Response<ArticleResponse>
}