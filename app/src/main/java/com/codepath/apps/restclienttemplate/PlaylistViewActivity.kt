package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.codepath.apps.restclienttemplate.adapters.PLAYLIST_EXTRA

class PlaylistViewActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_view)
        val id = intent.getStringExtra(PLAYLIST_EXTRA)
    }
}