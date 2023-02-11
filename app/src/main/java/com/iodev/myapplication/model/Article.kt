package com.iodev.myapplication.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(
    tableName = "articles"
)
data class Article(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String?,
    var content: String?,
    var description: String?,
    var publishedAt: String?,
    var source: Source?,
    var title: String?,
    var url: String?,
    var urlToImage: String?
):Serializable