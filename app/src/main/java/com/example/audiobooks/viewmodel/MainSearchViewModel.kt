package com.example.myapplication.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.audiobooks.model.Podcasts
import com.example.myapplication.retrofit.RetrofitInstance
import kotlinx.coroutines.*

class MainSearchViewModel : ViewModel() {
    private val podcastResponse = MutableLiveData<Podcasts>()
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

       handlejob = viewModelScope.launch(Dispatchers.IO + exceptionHandler) {

            val podcastDetails = RetrofitInstance.getApiService().getPodcastList("Romantic")
           Log.d("jsondataaaaa","${podcastDetails.body()}")
           withContext(Dispatchers.Main) {
                if (podcastDetails.isSuccessful) {
                    podcastResponse.postValue(podcastDetails.body())
                } else {
                     Log.d("Error","APIFAILURE")
                }
            }

        }
    }

    fun getUserDetails(): MutableLiveData<Podcasts> {
        return podcastResponse
    }

}