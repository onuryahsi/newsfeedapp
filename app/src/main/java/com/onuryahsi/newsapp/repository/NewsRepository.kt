package com.onuryahsi.newsapp.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.onuryahsi.newsapp.dao.ArticleDao
import com.onuryahsi.newsapp.schema.Article
import javax.inject.Inject

class NewsRepository @Inject constructor() {

    @Inject
    lateinit var articleDao: ArticleDao

    fun getAll(): LiveData<List<Article>> {
        return articleDao.getAll()
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(article: Article) {
        articleDao.insert(article)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun update(article: Article) {
        articleDao.update(article)
    }

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun delete(article: Article) {
        articleDao.delete(article)
    }

    fun findByUrl(url: String): LiveData<Article> {
        return articleDao.findByUrl(url)
    }

}