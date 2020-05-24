package com.susheelkaram.myread.db.feeds_list

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.susheelkaram.myread.utils.Constants

/**
 * Created by Susheel Kumar Karam
 * Website - SusheelKaram.com
 */
@Entity(tableName = Constants.TABLE_NAME_FEEDLIST)
data class Feed(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val title: String,
    val url: String,
    val imageUrl: String,
    val description: String
)