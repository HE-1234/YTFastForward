package com.codepath.apps.restclienttemplate.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.CompoundButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.codepath.apps.restclienttemplate.R
import com.google.api.services.youtube.model.PlaylistItem
import com.google.api.services.youtube.model.SearchResult

class VideoViewAdapter (private val context: Context, private val videos: MutableList<PlaylistItem>): RecyclerView.Adapter<VideoViewAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoViewAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoViewAdapter.ViewHolder, position: Int) {
        val vid = videos[position]
        holder.tvTitle.text = vid.snippet.title.toString()
        holder.tvDescription.text = vid.snippet.description.toString()
        Glide.with(holder.itemView).load(vid.snippet.thumbnails.default.url.toString()).into(holder.ivVideo)
        holder.cbVideo.maxWidth = 0
    }

    override fun getItemCount(): Int {
        return videos.size
    }

    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvTitle = itemView.findViewById<TextView>(R.id.tvVidTitle)
        val ivVideo = itemView.findViewById<ImageView>(R.id.ivVideo)
        val tvDescription = itemView.findViewById<TextView>(R.id.tvVidDescription)
        val cbVideo = itemView.findViewById<CheckBox>(R.id.cb_video)
        init {
            itemView.setOnClickListener(this)
        }
        override fun onClick(v: View?) {

        }
    }
}