package com.iodev.myapplication

data class NewsReponse(
    val articles: List<Article>,
    val status: String,
    val totalResults: Int
)