package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audiobooks.model.PodcastFavourite
import com.example.audiobooks.repositories.MainActivityRepository
import kotlinx.coroutines.*

class MainSearchViewModel(private val repository: MainActivityRepository) : ViewModel() {
    private val podcastResponse = MutableLiveData<List<PodcastFavourite>>()
    private var handlejob: Job? = null


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }


    override fun onCleared() {
        super.onCleared()
        handlejob?.cancel()
    }


    fun getUserDetails(searchQuery: String) {

        handlejob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            repository.fetchDataFromApi()

            withContext(Dispatchers.Main) {

                val podcastFlow = repository.getPodcastFromLocal()
                podcastResponse.value = podcastFlow
            }

        }

//       handlejob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
//
//            val podcastDetails = RetrofitInstance.getApiService().getPodcastList("Romantic")
//           Log.d("jsondataaaaa","${podcastDetails.body()}")
//           withContext(Dispatchers.Main) {
//                if (podcastDetails.isSuccessful) {
//                    podcastResponse.postValue(podcastDetails.body())
//                } else {
//                     Log.d("Error","APIFAILURE")
//                }
//            }
//
//        }
    }

    fun getUserDetails(): MutableLiveData<List<PodcastFavourite>> {
        return podcastResponse
    }

}