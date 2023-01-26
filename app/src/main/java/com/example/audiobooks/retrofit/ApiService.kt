package com.example.myapplication.retrofit

import com.example.audiobooks.model.Podcasts
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search?")
    suspend fun getPodcastList(@Query("q") query: String): Response<Podcasts>
}