package com.example.news.MyViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.news.Repo.Repo
import com.example.news.models.NewsModels
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class MyViewModel : ViewModel() {

    private val _response = MutableStateFlow<NewsModels?>(null)
    val response: StateFlow<NewsModels?> = _response

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val repo = Repo()

    init {
        getNewsByCategory("general")
    }

    // Updated function with searchQuery support
    fun getNewsByCategory(category: String, searchQuery: String = "") {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = ""

            try {
                // Assuming your Repo.newsProvider supports a 'query' param (search keyword)
                val result = repo.newsProvider(
                    country = "us",
                    category = category,
                    query = if (searchQuery.isBlank()) null else searchQuery
                )

                if (result.isSuccessful) {
                    _response.value = result.body()
                } else {
                    _errorMessage.value = "Error: ${result.code()}"
                }

            } catch (e: Exception) {
                _errorMessage.value = "Exception: ${e.localizedMessage ?: "Unknown error"}"
            }

            _isLoading.value = false
        }
    }
}
