package com.example.audiobooks

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.audiobooks.adapter.PodcastsAdapter
import com.example.audiobooks.databinding.ActivityMainBinding
import com.example.audiobooks.model.PodcastFavourite
import com.example.myapplication.viewmodel.MainSearchViewModel
import com.example.myapplication.viewmodel.MainSearchViewModelFactory

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: MainSearchViewModel
    private lateinit var podcastsFavourite: PodcastFavourite
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        viewModel = ViewModelProvider(this@MainActivity, MainSearchViewModelFactory()).get(
            MainSearchViewModel::class.java
        )
        val lists = viewModel.getUserDetails("romantic")

        val adapter = PodcastsAdapter()
        binding.recyclerPodcasts.layoutManager = LinearLayoutManager(this)
        binding.recyclerPodcasts.adapter = adapter

        viewModel.getUserDetails().observe(this) {
            Log.d("DataMAinActivity", "name,{$it.get(0).fullName}")
            adapter.setProductListData(it.results)
            adapter.notifyDataSetChanged()
        }

//      podcastsFavourite = PodcastFavourite()
        Log.d("datassss", "{${lists.toString()}")
    }
}