package com.example.audiobooks

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.audiobooks.databinding.ActivityPodcastsDetailBinding
import com.example.audiobooks.localDataSource.PodcastDatabase
import com.example.audiobooks.model.PodcastFavourite
import com.example.audiobooks.repositories.MainActivityRepository
import com.example.audiobooks.viewmodel.PodCastDetailViewModel
import com.example.myapplication.retrofit.RetrofitInstance
import com.example.myapplication.viewmodel.MainSearchViewModelFactory

class PodcastsDetailActivity : AppCompatActivity() {

    private lateinit var viewModel: PodCastDetailViewModel
    private lateinit var podcastsFavourite: PodcastFavourite
    private lateinit var binding: ActivityPodcastsDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPodcastsDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val podcastDao = PodcastDatabase.getInstanceDatabase(applicationContext).getPodcastDao()
        val apiService = RetrofitInstance.getApiService()
        val repository = MainActivityRepository(podcastDao, apiService)

        viewModel =
            ViewModelProvider(this@PodcastsDetailActivity, MainSearchViewModelFactory(repository)).get(
                PodCastDetailViewModel::class.java
            )

        val intent = getIntent()
        val podcastFavourite = intent.getSerializableExtra("PodcastDetail") as PodcastFavourite
        binding.tvPodcastName.text = podcastFavourite.title_highlighted
        binding.tvPodcastAuthor.text = podcastFavourite.publisher_highlighted
        Glide.with(this).load(podcastFavourite.image).into(binding.imgViewPodcast)
        binding.tvPodcastFavourite.setOnClickListener {
            if (!podcastFavourite.is_favourite) {
                binding.tvPodcastFavourite.text = getString(R.string.favourited)
                binding.tvPodcastFavourite.setBackgroundColor(Color.DKGRAY)
                viewModel.updatePodcastFavourite(true, podcastFavourite.title_highlighted)
            } else {
                binding.tvPodcastFavourite.text = getString(R.string.favourite)
                binding.tvPodcastFavourite.setBackgroundColor(Color.GREEN)
                viewModel.updatePodcastFavourite(false, podcastFavourite.title_highlighted)
            }
        }


    }
}