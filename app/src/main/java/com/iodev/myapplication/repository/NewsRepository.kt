package com.iodev.myapplication.repository

import com.iodev.myapplication.api.RetrofitInstance
import com.iodev.myapplication.db.ArticleDatabase
import com.iodev.myapplication.model.Article

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNews(countryCode: String, category: String) =
        RetrofitInstance.api.getBreakingNew(countryCode,category)

    suspend fun searchNews(searchQuery: String, pageNumber: Int)=
        RetrofitInstance.api.searchForNews(searchQuery,"2023-01-10", pageNumber)

    suspend fun upsert(article: Article) =
        db.getArticleDao().upsert(article)

    fun getSaveNews() = db.getArticleDao().getAllArticles()

    suspend fun deleteArticle(article: Article) =
        db.getArticleDao().deleteArticle(article)

}