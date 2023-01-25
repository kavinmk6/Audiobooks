package com.example.audiobooks.model

data class Podcasts(
    val count: Int,
    val next_offset: Int,
    val results: List<Result>,
    val took: Double,
    val total: Int
)