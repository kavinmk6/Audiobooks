package com.example.audiobooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.audiobooks.databinding.ActivityMainBinding
import com.example.audiobooks.model.PodcastFavourite
import com.example.myapplication.viewmodel.MainSearchViewModel
import com.example.myapplication.viewmodel.MainSearchViewModelFactory

class PodcastsDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: MainSearchViewModel
    private lateinit var podcastsFavourite: PodcastFavourite
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        viewModel = ViewModelProvider(this@PodcastsDetailActivity, MainSearchViewModelFactory()).get(
//            MainSearchViewModel::class.java
//        )

        


    }
}