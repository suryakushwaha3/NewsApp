package com.example.news.MyViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.Repo.Repo
import com.example.news.models.Article
import com.example.news.models.NewsModels
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.io.IOException

class MyViewModel : ViewModel() {

    // Response data from API

    private val _response = MutableStateFlow<NewsModels?>(null)
    val response: StateFlow<NewsModels?> = _response

    // Loading state for showing progress

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Error message for UI

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    // Currently selected article (for detail screen)

    private val _selectedArticle = MutableStateFlow<Article?>(null)
    val selectedArticle: StateFlow<Article?> = _selectedArticle

    // Repository instance

    private val repo = Repo()

    // Initial fetch with default category

    init {
        getNewsByCategory("general")
    }


     //Fetch news by category and optional search query

    fun getNewsByCategory(category: String, searchQuery: String = "") {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""

            try {
                val result = repo.newsProvider(
                    country = "us",
                    category = category,
                    query = if (searchQuery.isBlank()) null else searchQuery
                )

                if (result.isSuccessful) {
                    _response.value = result.body()
                } else {
                    _errorMessage.value = "Error: ${result.code()} - ${result.message()}"
                }

            } catch (e: IOException) {
                _errorMessage.value = "No internet connection."
            } catch (e: Exception) {
                _errorMessage.value = "Exception: ${e.localizedMessage ?: "Unknown error"}"
            }

            _isLoading.value = false
        }
    }


     //Select article for detail screen

    fun selectArticle(article: Article) {
        _selectedArticle.value = article
    }
}
