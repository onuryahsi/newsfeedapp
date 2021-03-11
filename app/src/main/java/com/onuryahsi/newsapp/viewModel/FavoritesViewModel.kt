package com.onuryahsi.newsapp.viewModel

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.onuryahsi.newsapp.repository.NewsRepository
import com.onuryahsi.newsapp.schema.Article
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    application: Application,
    private val repository: NewsRepository,
) : AndroidViewModel(application) {

    fun getAll() = repository.getAll()

    fun insert(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.insert(article)
        }
    }

    fun update(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.update(article)
        }
    }

    fun delete(article: Article) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.delete(article)
        }
    }
}