package com.example.news.Repo

import com.example.news.Network.APiProvider
import com.example.news.models.NewsModels
import retrofit2.Response

class Repo {

    suspend fun newsProvider(
        country: String,
        category: String,
        query: String?,
    ) :Response<NewsModels>{
        return APiProvider.providerApi().getTopHeadlines(
            country = country,
            category = category
        )

    }


}