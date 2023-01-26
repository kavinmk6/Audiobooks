package com.example.audiobooks.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.audiobooks.R
import com.example.audiobooks.model.Podcasts
import com.example.audiobooks.model.Result;

class PodcastsAdapter : RecyclerView.Adapter<PodcastsAdapter.MyViewHolder>() {

    var podcastList = emptyList<Result>()
    var onItemClick: ((Podcasts) -> Unit)? = null
    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.findViewById(R.id.tvPodcastName)
        var tvAuthor: TextView = view.findViewById(R.id.tvPodcastAuthor)
        var tvfavourite: TextView = view.findViewById(R.id.tvPodcastFavourite)
        var imageView: ImageView = view.findViewById(R.id.imgViewPodcast)

        init {
            itemView.setOnClickListener {
//                onItemClick?.invoke(Podcasts[adapterPosition])
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_layout_podcasts, parent, false)
        return MyViewHolder(itemView)
    }

    override fun getItemCount() = podcastList.size

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvName.text = podcastList[position].podcast.title_highlighted
        holder.tvAuthor.text = podcastList[position].podcast.publisher_highlighted
        holder.tvfavourite
        Glide.with(holder.itemView.context).load(podcastList[position].image).into(holder.imageView)
    }

    fun setProductListData(podcastResult: List<Result>) {
        podcastList = podcastResult
    }

}

