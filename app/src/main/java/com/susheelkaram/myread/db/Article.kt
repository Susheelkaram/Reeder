package com.susheelkaram.myread.db

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Entity
data class Article(
    @PrimaryKey(autoGenerate = true) val id: Int,
    var guid: String? = null,
    var title: String? = null,
    var author: String? = null,
    var link: String? = null,
    var pubDate: String? = null,
    var description: String? = null,
    var content: String? = null,
    var image: String? = null,
    var audio: String? = null,
    var sourceName: String? = null,
    var sourceUrl: String? = null,
    var categories: MutableList<String> = mutableListOf()
)