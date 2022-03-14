package com.codepath.apps.restclienttemplate

import com.codepath.apps.restclienttemplate.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.api.services.youtube.model.Playlist
import com.google.api.services.youtube.model.SearchListResponse

class AutomaticPlaylistCreator : AppCompatActivity() {
    val client = RestApplication.getYoutubeClient(this)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automatic_playlist_creator)
        val accessToken = intent.getStringExtra("accessToken")
        Log.i("AutoPlayListCreator","The access Token is recieved $accessToken")

        val GeneraterButton = findViewById<Button>(R.id.generateBtn)
        val PlayListTypeTV = findViewById<TextView>(R.id.PlayListNameTV)

        GeneraterButton.text
        GeneraterButton.setOnClickListener() {
            val keyword = PlayListTypeTV.text.toString()
            client.createManualPlaylist(keyword, "", object: YoutubeResponseHandler<Playlist>() {
                override fun onSuccess(playlist: Playlist) {
                    client.getSearchResult(
                        keyword,
                        object : YoutubeResponseHandler<SearchListResponse>() {
                            override fun onSuccess(json: SearchListResponse) {
                            var count = 0
                            while(count < 10) {
//                                val rnds = (0..24).random()
                                val jsonObj = json.items[count]
                                if (jsonObj.id != null && jsonObj.id.videoId != null) {
                                    client.addVideoToPlaylist(playlist.id, jsonObj.id.videoId)
                                }
                               count += 1
                            }
                                Toast.makeText(this@AutomaticPlaylistCreator, "Success", Toast.LENGTH_SHORT).show()
                                finish()
                            }

                            override fun onFailure(response: SearchListResponse) {
                                Log.i(TAG, "Error")
                            }

                        })
                }

                override fun onFailure(response: Playlist) {
                    TODO("Not yet implemented")
                }
            })
        }
    }


    companion object {
        const val TAG = "AutomaticPlayList"
    }
}