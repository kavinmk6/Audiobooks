package com.example.audiobooks

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.audiobooks.adapter.PodcastsAdapter
import com.example.audiobooks.databinding.ActivityMainBinding
import com.example.audiobooks.localDataSource.PodcastDatabase
import com.example.audiobooks.model.PodcastFavourite
import com.example.audiobooks.model.Resource
import com.example.audiobooks.repositories.MainActivityRepository
import com.example.myapplication.retrofit.RetrofitInstance
import com.example.myapplication.viewmodel.MainSearchViewModel
import com.example.myapplication.viewmodel.MainSearchViewModelFactory

class MainActivity : AppCompatActivity() {

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

        val adapter = PodcastsAdapter()
        binding.recyclerPodcasts.layoutManager = LinearLayoutManager(this)
        binding.recyclerPodcasts.adapter = adapter

        adapter.onItemClick = { podcastFavourite ->
            val intent = Intent(this, PodcastsDetailActivity::class.java)
            intent.putExtra("PodcastDetail", podcastFavourite)
            startActivity(intent)
        }

        viewModel.getUserDetails().observe(this) {

            when (it) {
                is Resource.Success -> {
                    it.data?.let { it1 -> adapter.setProductListData(it1) }
                    adapter.notifyDataSetChanged()

                }
                is Resource.Error -> {
                    binding.tvApiError.visibility = View.VISIBLE
                }
                is Resource.Loading -> {
                    if (it.isLoading == true) {
                        binding.progressBar.visibility = View.VISIBLE
                    } else {
                        binding.progressBar.visibility = View.GONE
                    }
                }
            }

        }
    }

    override fun onResume() {
        super.onResume()
        viewModel.getUserDetails("romantic")
    }
}