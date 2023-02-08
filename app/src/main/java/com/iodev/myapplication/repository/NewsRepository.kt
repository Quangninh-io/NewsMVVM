package com.iodev.myapplication.repository

import com.iodev.myapplication.api.RetrofitInstance
import com.iodev.myapplication.db.ArticleDao
import com.iodev.myapplication.db.ArticleDatabase

class NewsRepository(
    val db: ArticleDatabase
) {
    suspend fun getBreakingNew(countryCode: String, pageNumber: Int) =
        RetrofitInstance.api.getBreakingNew(countryCode,pageNumber)
}