package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audiobooks.model.PodcastFavourite
import com.example.audiobooks.model.Resource
import com.example.audiobooks.repositories.MainActivityRepository
import kotlinx.coroutines.*

class MainSearchViewModel(private val repository: MainActivityRepository) : ViewModel() {
    private val podcastResponse = MutableLiveData<Resource<List<PodcastFavourite>>>()
    private var handlejob: Job? = null


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        podcastResponse.postValue(Resource.Error("API ERROR..."))
        podcastResponse.postValue(Resource.Loading(false))
    }

    override fun onCleared() {
        super.onCleared()
        handlejob?.cancel()
    }

    fun getUserDetails(searchQuery: String) {
        podcastResponse.postValue(Resource.Loading(true))
        handlejob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.fetchDataFromApi(searchQuery)
//            Resource.Loading<Boolean>(false)

            withContext(Dispatchers.Main) {
                val podcastFlow = repository.getPodcastFromLocal()
                podcastResponse.postValue(Resource.Loading(false))
                podcastResponse.postValue(Resource.Success(podcastFlow))
            }
        }
    }

    fun getUserDetails(): MutableLiveData<Resource<List<PodcastFavourite>>> {
        return podcastResponse
    }

}