package com.onuryahsi.newsapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.switchMap
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.google.gson.Gson
import com.onuryahsi.newsapp.model.ArticleResponse
import com.onuryahsi.newsapp.model.ErrorResponse
import com.onuryahsi.newsapp.repository.NewsPagingRepository
import com.onuryahsi.newsapp.repository.NewsRepository
import com.onuryahsi.newsapp.schema.Article
import com.onuryahsi.newsapp.service.NewsRestAPI
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    application: Application,
    private val repository: NewsRepository
) :
    AndroidViewModel(application) {

    var articleLiveData = MutableLiveData<ArticleResponse>()

//    private var logging = HttpLoggingInterceptor().apply {
//        setLevel(HttpLoggingInterceptor.Level.BASIC)
//    }

//    private var httpClient = OkHttpClient.Builder().addInterceptor(logging).build()
//
//
//    var retrofitClient   : NewsRestAPI = Retrofit.Builder()
//        .addConverterFactory(GsonConverterFactory.create())
//        .baseUrl(NewsRestAPI.BASE_URL)
//        .client(httpClient)
//        .build()
//        .create(NewsRestAPI::class.java)

    @Inject
    lateinit var retrofitClient:NewsRestAPI

    fun getArticles(q: String, page: Int) {
        viewModelScope.launch {
            val response = retrofitClient.getArticles(q, page, NewsRestAPI.api_key)
            if (response.isSuccessful) {
                articleLiveData.postValue(response.body())
            } else {
                val errorResult =
                    Gson().fromJson(
                        response.errorBody()?.string(),
                        ErrorResponse::class.java
                    )

                println("Error : 1 - " + errorResult.status)
                println("Error : 2 - " + errorResult.code)
                println("Error : 3 - " + errorResult.message)
            }
        }
    }

    @Inject
    lateinit var pagingRepository: NewsPagingRepository
    //val pagingRepository: NewsPagingRepository = NewsPagingRepository(retrofitClient)

    private val queryLiveData = MutableLiveData<String>()

    val articles = queryLiveData.switchMap { queryString ->
        pagingRepository.getNews(queryString).cachedIn(viewModelScope)
    }

    fun searchArticles(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    // Room
    fun saveArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(article)
        }
    }

    var resultArticle = MutableLiveData<Article>()

    fun searchByUrl(url: String) {
        viewModelScope.launch(Dispatchers.IO) {
            resultArticle.postValue(repository.findByUrl(url))
        }
    }

    fun removeArticle(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(article)
        }
    }
}