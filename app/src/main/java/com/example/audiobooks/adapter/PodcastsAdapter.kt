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
import com.example.audiobooks.model.PodcastFavourite
import com.example.audiobooks.model.Podcasts
import com.example.audiobooks.model.Result;

class PodcastsAdapter : RecyclerView.Adapter<PodcastsAdapter.MyViewHolder>() {

    var podcastList = emptyList<PodcastFavourite>()
    var onItemClick: ((PodcastFavourite) -> Unit)? = null

    inner class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var tvName: TextView = view.findViewById(R.id.tvPodcastName)
        var tvAuthor: TextView = view.findViewById(R.id.tvPodcastAuthor)
        var tvfavourite: TextView = view.findViewById(R.id.tvPodcastFavourite)
        var imageView: ImageView = view.findViewById(R.id.imgViewPodcast)

        init {
            itemView.setOnClickListener {
                onItemClick?.invoke(podcastList[adapterPosition])
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
        holder.tvName.text = podcastList[position].title_highlighted
        holder.tvAuthor.text = podcastList[position].publisher_highlighted
        holder.tvfavourite.visibility = if(!podcastList[position].is_favourite) View.VISIBLE else View.GONE
        Glide.with(holder.itemView.context).load(podcastList[position].image).into(holder.imageView)
//        holder.itemView.setOnClickListener{
//            onItemClick.onClickPodcast(podcastList[position])
//        }
    }

    fun setProductListData(podcastResult: List<PodcastFavourite>) {
        podcastList = podcastResult
    }

//    interface onItemClick{
//        fun onClickPodcast(podcastFavourite:PodcastFavourite)
//    }

}

