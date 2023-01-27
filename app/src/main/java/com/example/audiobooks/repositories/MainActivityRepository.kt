package com.example.audiobooks.repositories

import android.app.Application
import android.content.Context
import android.provider.ContactsContract
import android.util.Log
import androidx.lifecycle.LiveData
import com.example.audiobooks.localDataSource.PodcastDao
import com.example.audiobooks.localDataSource.PodcastDatabase
import com.example.audiobooks.model.PodcastFavourite
import com.example.myapplication.retrofit.ApiService
import com.example.myapplication.retrofit.RetrofitInstance
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext

class MainActivityRepository(val podcastDao: PodcastDao, val apiService: ApiService) {


    suspend fun fetchDataFromApi() {
        if (podcastDao.getAllPodcast().isEmpty()) {
            val podcastResults = apiService.getPodcastList("Romantic")
            val results = podcastResults.body()?.results
            if (results != null) {
                for (podcast in results) {
                    val p = PodcastFavourite(
                        podcast.podcast.title_original,
                        podcast.podcast.publisher_highlighted,
                        podcast.image,
                        false
                    )
                    podcastDao.insertPodcast(p)
                }
            }
        }
    }

    suspend fun getPodcastFromLocal(): List<PodcastFavourite> {
        return podcastDao.getAllPodcast()
    }
}