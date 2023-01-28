package com.example.audiobooks.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audiobooks.repositories.MainActivityRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PodCastDetailViewModel(val mainActivityRepository: MainActivityRepository) : ViewModel() {


    fun updatePodcastFavourite(isFavourite: Boolean, podcastId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            mainActivityRepository.podcastDao.updatePodcastFavourite(isFavourite, podcastId)
        }
    }


}