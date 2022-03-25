package com.codepath.apps.restclienttemplate

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.adapters.PLAYLIST_EXTRA
import com.codepath.apps.restclienttemplate.adapters.VideoAdapter
import com.codepath.apps.restclienttemplate.adapters.VideoViewAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView
import com.google.api.services.youtube.model.*


class PlaylistViewActivity : YouTubeBaseActivity() {
    lateinit var rvVideos: RecyclerView
    lateinit var videoAdapter: VideoViewAdapter
    lateinit var client: YoutubeClient
    lateinit var id :String
    lateinit var youtubePlayer: YouTubePlayer
    val videos = mutableListOf<PlaylistItem>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_view)
        id = intent.getStringExtra(PLAYLIST_EXTRA)
        client = RestApplication.getYoutubeClient(this)
        val youTubePlayerView = findViewById<View>(R.id.player) as YouTubePlayerView
        youTubePlayerView.initialize("YOUR API KEY",
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) {
                    // do any work here to cue video, play video, etc.
                    youtubePlayer = youTubePlayer
                    youTubePlayer.cuePlaylist(id)
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                }
            })
        rvVideos = findViewById(R.id.rvVideoView)
        rvVideos.layoutManager = LinearLayoutManager(this)
        videoAdapter = VideoViewAdapter(this, videos, ::onRecyclerItemClick)
        rvVideos.adapter = videoAdapter
        getVideos(id)
        var mBotton = findViewById<FloatingActionButton>(R.id.btnCreate);
        mBotton.setOnClickListener(View.OnClickListener() {
            val intent = Intent(this@PlaylistViewActivity, EditActivity::class.java)
            intent.putExtra(PLAYLIST_EXTRA, id)
            startActivityForResult(intent,100)
        })

    }

    fun onRecyclerItemClick(index: Int) {
        youtubePlayer.cuePlaylist(id, index, 0)
    }

    fun getVideos(id:String){
        client.retrievePlaylistItems(id,  object : YoutubeResponseHandler<PlaylistItemListResponse>() {
            override fun onSuccess(json: PlaylistItemListResponse) {
                videos.clear()
                for (vid in json.items) {
                    videos.add(vid)
                }
                videoAdapter.notifyDataSetChanged()
            }
            override fun onFailure(response: PlaylistItemListResponse) {
                Log.i(TAG,"Error")
            }
        })
    }
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if(resultCode == RESULT_OK && requestCode == 100){
            getVideos(id)
            videoAdapter.notifyDataSetChanged()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
    companion object {
        const val TAG = "PlaylistViewActivity"
    }
}