package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.audiobooks.repositories.MainActivityRepository
import com.example.audiobooks.viewmodel.PodCastDetailViewModel
import java.lang.IllegalArgumentException

class MainSearchViewModelFactory(val repository: MainActivityRepository): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainSearchViewModel::class.java)){
            MainSearchViewModel(repository) as T
        }
        else if(modelClass.isAssignableFrom(PodCastDetailViewModel::class.java)){
            PodCastDetailViewModel(repository) as T
        }
        else{
            throw IllegalArgumentException("MainSearchViewModel not found")
        }
    }
}