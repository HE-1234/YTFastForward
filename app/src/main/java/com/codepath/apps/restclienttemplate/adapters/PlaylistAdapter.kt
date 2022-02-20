package com.codepath.apps.restclienttemplate.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.models.Playlist


class PlaylistAdapter(val playlists: ArrayList<Playlist>): RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.playlist_item, parent, false)
        return  ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistAdapter.ViewHolder, position: Int) {
        val playlist = playlists.get(position)
        holder.tvTitle.text = playlist.title
    }

    override fun getItemCount(): Int {
        return playlists.size
    }


    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val tvTitle = itemView.findViewById<TextView>(R.id.tvPytitle)
    }

}