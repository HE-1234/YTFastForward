package com.codepath.apps.restclienttemplate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.R
import com.google.api.services.youtube.model.SearchResult


class VideoAdapter(private val context: Context, private val videos: MutableList<SearchResult>): RecyclerView.Adapter<VideoAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoAdapter. ViewHolder, position: Int) {
        val video = videos[position]
        holder.tvTitle.text = video.snippet.title.toString()
        holder.tvDescription.text = video.snippet.description.toString()
        Glide.with(holder.itemView).load(video.snippet.thumbnails.default.url.toString()).into(holder.ivVideo)
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvTitle = itemView.findViewById<TextView>(R.id.tvVidTitle)
        val ivVideo = itemView.findViewById<ImageView>(R.id.ivVideo)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvVidDescription)
        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val playlist: SearchResult = videos[adapterPosition]
        }
    }



}