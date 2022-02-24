package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.adapters.PlaylistAdapter
import com.codepath.apps.restclienttemplate.models.Playlist

class MainActivity2 : AppCompatActivity() {

    val playlists =  ArrayList<Playlist>()
    lateinit var rvPlaylists: RecyclerView
    lateinit var playListAdapter: PlaylistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        rvPlaylists = findViewById(R.id.rvPlaylist)
        rvPlaylists.layoutManager = LinearLayoutManager(this)
        playListAdapter = PlaylistAdapter(playlists)
        rvPlaylists.adapter = playListAdapter
    }


    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.menuIcon){
            Toast.makeText(this, "Menu icon clicked", Toast.LENGTH_SHORT).show()
        }

        return super.onOptionsItemSelected(item)
    }



    companion object{
        const val TAG = "MainActivity2"
    }
}