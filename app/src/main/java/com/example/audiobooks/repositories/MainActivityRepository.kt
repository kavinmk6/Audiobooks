package com.example.audiobooks.repositories

import android.util.Log
import com.example.audiobooks.localDataSource.PodcastDao
import com.example.audiobooks.model.PodcastFavourite
import com.example.myapplication.retrofit.ApiService

class MainActivityRepository(val podcastDao: PodcastDao, val apiService: ApiService) {


    suspend fun fetchDataFromApi(searchQuery: String) {

        try {
            if (podcastDao.getAllPodcast().isEmpty()) {
                val podcastResults = apiService.getPodcastList(searchQuery)
                val results = podcastResults.body()?.results
                if (results != null) {
                    for (result in results) {
                        val p = PodcastFavourite(
                            result.podcast.title_original,
                            result.podcast.publisher_original,
                            result.podcast.thumbnail,
                            result.description_original,
                            false
                        )
                        podcastDao.insertPodcast(p)
                    }
                }
            }
        } catch (e: Exception) {
            Log.d("stacktraceeee", "${e.localizedMessage}")
        }
    }

    suspend fun getPodcastFromLocal(): List<PodcastFavourite> {
        return podcastDao.getAllPodcast()
    }
}