package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audiobooks.model.PodcastFavourite
import com.example.audiobooks.repositories.MainActivityRepository
import kotlinx.coroutines.*
import com.example.audiobooks.model.Resource

class MainSearchViewModel(private val repository: MainActivityRepository) : ViewModel() {
    private val podcastResponse = MutableLiveData<Resource<List<PodcastFavourite>>>()
    private var handlejob: Job? = null


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        podcastResponse.postValue(Resource.Error("API ERROR..."))
        Resource.Loading<Boolean>(false)
    }

    override fun onCleared() {
        super.onCleared()
        handlejob?.cancel()
    }


    fun getUserDetails(searchQuery: String) {
        handlejob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.fetchDataFromApi(searchQuery)
            Resource.Loading<Boolean>(true)

            withContext(Dispatchers.Main) {
                val podcastFlow = repository.getPodcastFromLocal()
                podcastResponse.postValue(Resource.Success(podcastFlow))
                Resource.Loading<Boolean>(false)
            }

        }
    }

    fun getUserDetails(): MutableLiveData<Resource<List<PodcastFavourite>>> {
        return podcastResponse
    }

}