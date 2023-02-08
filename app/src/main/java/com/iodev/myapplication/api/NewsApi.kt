package com.iodev.myapplication.api

import com.iodev.myapplication.model.NewsReponse
import com.iodev.myapplication.util.Constants.Companion.API_KEY
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("v2/top-headline")
    suspend fun getBreakingNew(
        @Query("country")
        contryCode: String ="us",
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String =API_KEY
    ): Response<NewsReponse>

    @GET("v2/everything")
    suspend fun searchForNews(
        @Query("q")
        searchQuery: String,
        @Query("page")
        pageNumber: Int = 1,
        @Query("apiKey")
        apiKey: String =API_KEY
    ): Response<NewsReponse>
}