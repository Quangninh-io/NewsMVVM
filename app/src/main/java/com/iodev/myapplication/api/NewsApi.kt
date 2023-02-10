package com.iodev.myapplication.api

import com.iodev.myapplication.model.NewsResponse
import com.iodev.myapplication.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headlines")
    suspend fun getBreakingNew(
        @Query("country")
        contryCode: String ="us",
        @Query("category")
        category: String = "business",
        @Query("apiKey")
        apiKey: String =API_KEY
    ): Response<NewsResponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("from")
        from: String,
        @Query("sortBy")
        sortBy: Int ,
        @Query("apiKey")
        apiKey: String =API_KEY
    ): Response<NewsResponse>
}