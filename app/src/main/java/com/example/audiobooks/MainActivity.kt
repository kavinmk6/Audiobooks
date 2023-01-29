package com.example.audiobooks

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.audiobooks.adapter.PodcastsAdapter
import com.example.audiobooks.databinding.ActivityMainBinding
import com.example.audiobooks.localDataSource.PodcastDatabase
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
                    binding.apply {
                        progressBar.visibility = View.GONE
                        tvApiError.visibility = View.GONE
                        recyclerPodcasts.visibility = View.VISIBLE

                    }
                    it.data?.let { it1 -> adapter.setProductListData(it1) }
                    adapter.notifyDataSetChanged()

                }
                is Resource.Error -> {
                    binding.apply {
                        progressBar.visibility = View.GONE
                        recyclerPodcasts.visibility = View.GONE
                        tvApiError.visibility = View.VISIBLE
                    }
                }
                is Resource.Loading -> {
                    binding.apply {
                        tvApiError.visibility = View.GONE
                        recyclerPodcasts.visibility = View.GONE
                        progressBar.visibility = View.VISIBLE
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