package com.example.news.models

data class NewsModels(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)