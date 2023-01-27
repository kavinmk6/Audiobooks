package com.example.audiobooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.audiobooks.adapter.PodcastsAdapter
import com.example.audiobooks.databinding.ActivityMainBinding
import com.example.audiobooks.localDataSource.PodcastDatabase
import com.example.audiobooks.model.PodcastFavourite
import com.example.audiobooks.repositories.MainActivityRepository
import com.example.myapplication.retrofit.RetrofitInstance
import com.example.myapplication.viewmodel.MainSearchViewModel
import com.example.myapplication.viewmodel.MainSearchViewModelFactory

class MainActivity : AppCompatActivity(){

    private lateinit var viewModel: MainSearchViewModel
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val podcastDao = PodcastDatabase.getInstanceDatabase(applicationContext).getPodcastDao()
        val apiService = RetrofitInstance.getApiService()
        val repository = MainActivityRepository(podcastDao, apiService)

        viewModel =
            ViewModelProvider(this@MainActivity, MainSearchViewModelFactory(repository)).get(
                MainSearchViewModel::class.java
            )

        val lists = viewModel.getUserDetails("romantic")

        val adapter = PodcastsAdapter()
        binding.recyclerPodcasts.layoutManager = LinearLayoutManager(this)
        binding.recyclerPodcasts.adapter = adapter

        adapter.onItemClick = { podcastFavourite ->
            val intent = Intent(this, PodcastsDetailActivity::class.java)
            intent.putExtra("PodcastDetail", podcastFavourite)
            startActivity(intent)
        }

        viewModel.getUserDetails().observe(this) {
            val n = it.get(0).title_highlighted
            Log.d("DataMAinActivity", "name,{$n}")
            adapter.setProductListData(it)
            adapter.notifyDataSetChanged()
            Toast.makeText(this, it.get(0).title_highlighted, Toast.LENGTH_LONG).show()
        }

        Log.d("datassss", "{${lists.toString()}")
    }
}