package com.onuryahsi.newsapp.di

import android.content.Context
import com.onuryahsi.newsapp.dao.ArticleDao
import com.onuryahsi.newsapp.database.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return AppDatabase.getDatabase(context)
    }

    @Singleton
    @Provides
    fun provideArticleDao(@ApplicationContext context: Context): ArticleDao {
        return AppDatabase.getDatabase(context).articleDao
    }
}