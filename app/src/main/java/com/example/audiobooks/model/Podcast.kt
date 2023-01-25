package com.example.audiobooks.model

data class Podcast(
    val genre_ids: List<Int>,
    val id: String,
    val image: String,
    val listen_score: Int,
    val listen_score_global_rank: String,
    val listennotes_url: String,
    val publisher_highlighted: String,
    val publisher_original: String,
    val thumbnail: String,
    val title_highlighted: String,
    val title_original: String
)