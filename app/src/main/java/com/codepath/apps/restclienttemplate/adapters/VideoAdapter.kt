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
import com.google.api.services.youtube.model.SearchResult


class VideoAdapter(private val context: Context, private val videos: MutableList<Pair<SearchResult, Boolean>>): RecyclerView.Adapter<VideoAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VideoAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.video_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: VideoAdapter.ViewHolder, position: Int) {
        val pair = videos[position]
        holder.tvTitle.text = pair.first.snippet.title.toString()
        holder.tvDescription.text = pair.first.snippet.description.toString()
        Glide.with(holder.itemView).load(pair.first.snippet.thumbnails.default.url.toString()).into(holder.ivVideo)
        holder.cbVideo.isChecked = pair.second
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
            cbVideo.setOnCheckedChangeListener(object : CompoundButton.OnCheckedChangeListener {
                override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                    val video: SearchResult = videos[adapterPosition].first
                    cbVideo.isChecked = isChecked
                    videos[adapterPosition] = Pair(video, isChecked)
                }
            })
        }

        override fun onClick(v: View?) {
            cbVideo.toggle()
        }
    }



}