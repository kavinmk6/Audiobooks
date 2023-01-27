package com.example.audiobooks.viewmodel

import androidx.lifecycle.ViewModel
import com.example.audiobooks.repositories.MainActivityRepository

class PodCastDetailViewModel(val mainActivityRepository: MainActivityRepository) :ViewModel() {

     fun updatePodcastFavourite(isFavourite:Boolean, podcastName:String){
        mainActivityRepository.podcastDao.updatePodcastFavourite(isFavourite,podcastName)
    }


}