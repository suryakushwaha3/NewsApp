package com.example.news.Network

import com.example.news.utils.API_KEY
import com.example.news.models.NewsModels
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=5cd86e52c1f747a998ce9aba9188fa95

    @GET("top-headlines")

    suspend fun getTopHeadlines(

        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = API_KEY



    ): Response<NewsModels>

}