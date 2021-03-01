package com.onuryahsi.newsapp.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.onuryahsi.newsapp.schema.Article

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(article: Article)

    @Update
    fun update(article: Article)

    @Delete
    fun delete(article: Article)

    @Query("SELECT * FROM articles order by id desc")
    fun getAll(): LiveData<List<Article>>

    @Query("SELECT * FROM articles where url = :url")
    fun findByUrl(url: String): Article
}