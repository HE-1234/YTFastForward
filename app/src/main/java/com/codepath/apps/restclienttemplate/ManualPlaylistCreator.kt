package com.codepath.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class ManualPlaylistCreator : AppCompatActivity() {

    lateinit var playlistName : EditText
    lateinit var playlistDesc : EditText
    val client = RestApplication.getYoutubeClient(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_manual_playlist_creator)

        playlistName = findViewById(R.id.playlistName)
        playlistDesc = findViewById(R.id.playlistDesc)
        
        findViewById<Button>(R.id.btnConfirm).setOnClickListener {
            Toast.makeText(this, "Making Playlist...", Toast.LENGTH_SHORT).show()

            val title = playlistName.text.toString()
            val description = playlistDesc.text.toString()

            if (title.isEmpty())
            {
                Toast.makeText(this, "Title cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else if (description.isEmpty())
            {
                Toast.makeText(this, "Description cannot be empty", Toast.LENGTH_SHORT).show()
            }
            else
            {
                client.createManualPlaylistNoHandler(title, description)
                finish()
            }
        }

        findViewById<Button>(R.id.btnClear).setOnClickListener {
            playlistName.getText().clear()
            playlistDesc.getText().clear()
        }
    }
}