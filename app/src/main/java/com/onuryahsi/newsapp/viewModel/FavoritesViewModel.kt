package com.onuryahsi.newsapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.onuryahsi.newsapp.repository.NewsRepository
import com.onuryahsi.newsapp.schema.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    application: Application,
    private val repository: NewsRepository,
) : AndroidViewModel(application) {

    fun getAll() = repository.getAll()

    suspend fun insert(article: Article) {
        viewModelScope.launch {
            repository.insert(article)
        }
    }

    suspend fun update(article: Article) {
        viewModelScope.launch {
            repository.update(article)
        }
    }

    suspend fun delete(article: Article) {
        viewModelScope.launch {
            repository.delete(article)
        }
    }
}