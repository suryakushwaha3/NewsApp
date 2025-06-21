package com.example.news.Network

import com.example.news.utils.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object APiProvider {

    val okHttpClient = OkHttpClient.Builder()

    fun providerApi() =
        Retrofit.Builder().baseUrl(BASE_URL)
            .client(okHttpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build().create(ApiService::class.java)


}