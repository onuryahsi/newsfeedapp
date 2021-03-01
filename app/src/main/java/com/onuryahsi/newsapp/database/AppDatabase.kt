package com.onuryahsi.newsapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.onuryahsi.newsapp.dao.ArticleDao
import com.onuryahsi.newsapp.schema.Article

@Database(entities = [Article::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract val articleDao: ArticleDao

    companion object {

        @Volatile
        private var appDatabase: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            if (appDatabase == null) {
                synchronized(this) {
                    appDatabase = Room.databaseBuilder(
                        context,
                        AppDatabase::class.java,
                        "news_database"
                    ).fallbackToDestructiveMigration().build()
                }
            }
            return appDatabase as AppDatabase
        }
    }
}