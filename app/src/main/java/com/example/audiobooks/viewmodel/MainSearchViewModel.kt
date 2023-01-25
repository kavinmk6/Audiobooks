package com.example.myapplication.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audiobooks.model.Podcasts
import com.example.myapplication.retrofit.RetrofitInstance
import kotlinx.coroutines.*

class MainSearchViewModel : ViewModel() {
    private val podcastResponse = MutableLiveData<MutableList<Podcasts>>()
    private var handlejob: Job? = null
    val loading = MutableLiveData<Boolean>()


    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->

    }


    override fun onCleared() {
        super.onCleared()
        handlejob?.cancel()
    }


    fun getUserDetails(searchQuery: String) {
        loading.value = true

        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {

            val podcastDetails = RetrofitInstance.getApiService().getPodcastList(searchQuery)
            withContext(Dispatchers.Main) {
                if (podcastDetails.isSuccessful) {
                    podcastResponse.postValue(podcastDetails.body())
                } else {
                    
                }
            }

        }
    }

    fun getUserDetails(): MutableList<Podcasts>? {
        return podcastResponse.value
    }

}