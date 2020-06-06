package com.susheelkaram.myread.db.articles

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.prof.rssparser.Article
import com.susheelkaram.myread.utils.Constants
import com.susheelkaram.myread.utils.Utils
import kotlinx.android.parcel.Parcelize

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Parcelize
@Entity(tableName = Constants.TABLE_NAME_FEED_ARTICLES)
data class FeedArticle(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    var guid: String = "",
    var title: String = "",
    var author: String = "",
    var link: String = "",
    @ColumnInfo(name = Constants.COL_PUB_DATE) var pubDate: Long = 0,
    var description: String = "",
    var content: String = "",
    var image: String = "",
    var audio: String = "",
    var sourceName: String = "",
    var sourceUrl: String = "",
    var categories: String = "",
    @ColumnInfo(name = Constants.COL_IS_READ) var isRead: Boolean = false,
    @ColumnInfo(name = Constants.COL_IS_BOOKMARKED) var isBookmarked: Boolean = false,
    @ColumnInfo(name = Constants.COL_FEED_ID) var feedId: Long = -1
) : Parcelable {
    companion object {
        fun from(article: Article): FeedArticle {
            return FeedArticle(
                guid = article.guid ?: "",
                title = article.title ?: "",
                author = article.author ?: "",
                link = article.link ?: "",
                pubDate = Utils.getEpochFromTimestamp(article.pubDate, inMillis = true) ?: 0,
                description = article.description ?: "",
                content = article.content ?: "",
                image = article.image ?: "",
                audio = article.audio ?: "",
                sourceName = article.sourceName ?: "",
                sourceUrl = article.sourceUrl ?: "",
                categories = article.categories.toString()
            )
        }
    }
}

//class Converters {
//    var moshi: Moshi
//    var stringListType: Type
//    var adapter: JsonAdapter<List<String>>
//
//    init {
//        moshi = Moshi.Builder().build()
//        stringListType = Types.newParameterizedType(List::class.java, String::class.java)
//        adapter = moshi.adapter(stringListType)
//    }
//
//    @TypeConverter
//    fun listFromString(string: String): List<String> {
//        return adapter.fromJson(string) ?: listOf()
//    }
//
//    @TypeConverter
//    fun stringFromList(list : List<String>): String {
//        return adapter.toJson(list)
//    }
//}