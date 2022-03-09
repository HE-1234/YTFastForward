package com.codepath.apps.restclienttemplate

import com.codepath.apps.restclienttemplate.R
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.google.api.services.youtube.model.SearchListResponse

class AutomaticPlaylistCreator : AppCompatActivity() {
    val client = RestApplication.getYoutubeClient(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_automatic_playlist_creator)

        val GeneraterButton = findViewById<Button>(R.id.generateBtn)
        val PlayListTypeTV = findViewById<TextView>(R.id.PlayListNameTV)

        GeneraterButton.setOnClickListener(){
            val keyword = PlayListTypeTV.text.toString()
            client.createManualPlaylist(keyword, "")

            client.getSearchResult("dog", object: YoutubeResponseHandler<SearchListResponse>(){
                override fun onSuccess(json: SearchListResponse) {
                    Log.i(MainActivity.TAG, json.items.toString())
                    Toast.makeText(this@AutomaticPlaylistCreator, "Success", Toast.LENGTH_SHORT).show()
                }

                override fun onFailure(response: SearchListResponse) {
                    Log.i(TAG, "Error")
                }

            })


        }

    }



    companion object {
        const val TAG = "AutomaticPlayList"
    }
}