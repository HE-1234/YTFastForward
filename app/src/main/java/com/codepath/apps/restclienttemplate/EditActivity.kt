package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuItemCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.adapters.PLAYLIST_EXTRA
import com.codepath.apps.restclienttemplate.adapters.PlaylistAdapter
import com.codepath.apps.restclienttemplate.adapters.VideoAdapter
import com.google.api.services.youtube.model.*


class EditActivity : AppCompatActivity() {
    lateinit var client: YoutubeClient
    val videos =  mutableListOf<Pair<SearchResult, Boolean>>()
    lateinit var rvVideos: RecyclerView
    lateinit var videoAdapter: VideoAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accessToken = intent.getStringExtra("accessToken")
        Log.i("Edit Activity","The access Token is recieved $accessToken")
        setContentView(R.layout.activity_edit)
        client = RestApplication.getYoutubeClient(this)
        rvVideos = findViewById(R.id.rvVideos)
        rvVideos.layoutManager = LinearLayoutManager(this)
        videoAdapter = VideoAdapter(this, videos)
        rvVideos.adapter = videoAdapter
        findViewById<Button>(R.id.btn_cancel).setOnClickListener {
            finish()
        }

        val id = intent.getStringExtra(PLAYLIST_EXTRA)
        findViewById<Button>(R.id.btn_addVideos).setOnClickListener {
            for (pair in videos) {
                if (pair.second) {
                    client.addVideoToPlaylist(id, pair.first.id.videoId)
                }
            }
            finish()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu) : Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.search, menu);
        val searchItem: MenuItem = menu.findItem(R.id.action_search);
        val searchView: SearchView = MenuItemCompat.getActionView(searchItem) as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // Fetch the data remotely
                client.getSearchResult(query, object : YoutubeResponseHandler<SearchListResponse>() {
                    override fun onSuccess(json: SearchListResponse) {
                        videos.clear()
                        for (video in json.items){
                            videos.add(Pair(video, false))
                        }
                        videoAdapter.notifyDataSetChanged()
                        //Log.i(TAG, videos.toString())
                    }
                    override fun onFailure(response: SearchListResponse) {
                        Log.i(TAG, "Error")
                    }
                })
                // Reset SearchView
                searchView.clearFocus()
                searchView.setQuery("", false)
                searchView.isIconified = true
                searchItem.collapseActionView()
                // Set activity title to search query
                this@EditActivity.setTitle(query)
                return true
            }

            override fun onQueryTextChange(s: String): Boolean {
                return false
            }
        })
        return true
    }
    companion object {
        const val TAG = "EditActivity"
    }
}
