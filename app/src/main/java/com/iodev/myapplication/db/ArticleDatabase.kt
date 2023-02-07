package com.iodev.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.iodev.myapplication.Article

@Database(
    entities = [Article::class],
    version = 1
)
abstract class ArticleDatabase : RoomDatabase(){
    abstract fun getArticleDao(): ArticleDao
}