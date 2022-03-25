package com.codepath.apps.restclienttemplate.adapters

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.PlaylistViewActivity
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.RestApplication
import com.google.api.services.youtube.model.Playlist

const val PLAYLIST_EXTRA = "PLAYLIST_EXTRA"


class PlaylistAdapter(private val context: Context, private val playlists: ArrayList<Playlist>): RecyclerView.Adapter<PlaylistAdapter.ViewHolder>() {

    val client = RestApplication.getYoutubeClient(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlaylistAdapter.ViewHolder {
        val context = parent.context
        val inflater = LayoutInflater.from(context)
        val view = inflater.inflate(R.layout.playlist_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlaylistAdapter. ViewHolder, position: Int) {
        val playlist = playlists.get(position)
        holder.tvTitle.text = playlist.snippet.localized.title
        holder.tvOptions.setOnClickListener {
            val popup = PopupMenu(context, holder.tvOptions)
            popup.inflate(R.menu.options_menu)

            popup.setOnMenuItemClickListener(PopupMenu.OnMenuItemClickListener { item: MenuItem? ->

                when (item!!.itemId) {
                    R.id.Option1 -> {
                        Toast.makeText(context, "WIll Delete", Toast.LENGTH_SHORT).show()
                        var playlistId = playlists[position].id
                        client.deletePlaylist(playlistId)
                    }
                    R.id.Option2 -> {
                        Toast.makeText(context, "WIll Share", Toast.LENGTH_SHORT).show()
                        val sendIntent: Intent = Intent().apply {
                            action = Intent.ACTION_SEND
                            val playlistLink = playlists[position].etag
                            putExtra(Intent.EXTRA_TEXT, playlistLink)
                            type = "text/plain"
                        }

                        val shareIntent = Intent.createChooser(sendIntent, "Sharing")
                        startActivity(context, shareIntent, null)

                    }
                }

                true
            })

            popup.show()
        }

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
        val tvOptions = itemView.findViewById<TextView>(R.id.tvOptions)

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