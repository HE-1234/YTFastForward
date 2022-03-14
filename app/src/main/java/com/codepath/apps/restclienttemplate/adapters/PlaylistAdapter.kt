package com.codepath.apps.restclienttemplate.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.PlaylistViewActivity
import com.codepath.apps.restclienttemplate.R
import com.google.api.services.youtube.model.Playlist

const val PLAYLIST_EXTRA = "PLAYLIST_EXTRA"

class PlaylistAdapter(private val context: Context, private val playlists: ArrayList<Playlist>): RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.playlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistAdapter. ViewHolder, position: Int) {
        val playlist = playlists.get(position)
        holder.tvTitle.text = playlist.snippet.localized.title

    }

    override fun getItemCount(): Int {
        return playlists.size
    }

    fun clear() {
        playlists.clear()
        notifyDataSetChanged()
    }

//    fun addAll(playlistList: List<Playlist>)
//    {
//        playlists.addAll(playlistList)
//        notifyDataSetChanged()
//    }
    
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView), View.OnClickListener{
        val tvTitle = itemView.findViewById<TextView>(R.id.tvPytitle)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val playlist: Playlist = playlists[adapterPosition]
            val intent = Intent(context, PlaylistViewActivity::class.java)
            intent.putExtra(PLAYLIST_EXTRA, playlist.id)
            context.startActivity(intent)
        }
    }

}