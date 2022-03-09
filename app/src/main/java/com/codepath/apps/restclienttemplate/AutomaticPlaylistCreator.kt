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

        val GeneraterButton = findViewById<Button>(R.id.generateBtn)
        val PlayListTypeTV = findViewById<TextView>(R.id.PlayListNameTV)


        GeneraterButton.setOnClickListener() {
            val keyword = PlayListTypeTV.text.toString()
            client.createManualPlaylist(keyword, "", object: YoutubeResponseHandler<Playlist>() {
                override fun onSuccess(playlist: Playlist) {
                    client.getSearchResult(
                        keyword,
                        object : YoutubeResponseHandler<SearchListResponse>() {
                            override fun onSuccess(json: SearchListResponse) {
                                for (i in json.items) {
                                    if (i.id != null && i.id.videoId != null) {
                                        client.addVideoToPlaylist(playlist.id, i.id.videoId)
                                    }
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