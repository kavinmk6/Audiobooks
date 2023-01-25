package com.example.myapplication.retrofit

import com.example.audiobooks.model.Podcasts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {

    @GET("search?q={query}")
    suspend fun getPodcastList(@Path("query") query: String): Response<MutableList<Podcasts>>
}