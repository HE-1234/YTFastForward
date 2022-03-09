package com.codepath.apps.restclienttemplate

import android.os.Bundle
import android.view.View
import com.codepath.apps.restclienttemplate.adapters.PLAYLIST_EXTRA
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView


class PlaylistViewActivity : YouTubeBaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_playlist_view)
        val id = intent.getStringExtra(PLAYLIST_EXTRA)

        val youTubePlayerView = findViewById<View>(R.id.player) as YouTubePlayerView

        youTubePlayerView.initialize("YOUR API KEY",
            object : YouTubePlayer.OnInitializedListener {
                override fun onInitializationSuccess(
                    provider: YouTubePlayer.Provider,
                    youTubePlayer: YouTubePlayer, b: Boolean
                ) {
                    // do any work here to cue video, play video, etc.
                    youTubePlayer.cuePlaylist(id)
                }

                override fun onInitializationFailure(
                    provider: YouTubePlayer.Provider,
                    youTubeInitializationResult: YouTubeInitializationResult
                ) {
                }
            })
    }
}