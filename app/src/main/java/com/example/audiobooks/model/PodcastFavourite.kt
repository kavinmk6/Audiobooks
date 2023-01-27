package com.example.audiobooks.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "podcast_table")
data class PodcastFavourite(
    @PrimaryKey
    @ColumnInfo(name = "podcastTitle" ) val title_highlighted: String,
    @ColumnInfo(name = "publisherName" )val publisher_highlighted: String,
    @ColumnInfo(name = "image" )val image: String,
    @ColumnInfo(name = "isFavoourite" )val is_favourite: Boolean,
) : Serializable

