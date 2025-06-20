package com.example.news

import android.R.attr.apiKey
import android.R.attr.category
import com.example.news.models.NewsModels
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {

    //https://newsapi.org/v2/top-headlines?country=us&category=business&apiKey=5cd86e52c1f747a998ce9aba9188fa95

    @GET("top-headlines")

    suspend fun ApiCall(

        @Query("country") country: String ="us",
        @Query("category") category: String ="business",
        @Query("apiKey") apiKey: String ="5cd86e52c1f747a998ce9aba9188fa95"


    ):Response<NewsModels>



}