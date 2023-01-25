package com.example.myapplication.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.IllegalArgumentException

class MainSearchViewModelFactory: ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(MainSearchViewModel::class.java)){
            MainSearchViewModel() as T
        }
        else{
            throw IllegalArgumentException("MainSearchViewModel not found")
        }
    }
}