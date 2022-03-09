package com.codepath.apps.restclienttemplate

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.adapters.PlaylistAdapter
import com.google.api.client.auth.oauth2.BearerToken
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.auth.oauth2.TokenResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.Playlist

class MainActivity2 : AppCompatActivity() {

    val playlists =  ArrayList<Playlist>()
    lateinit var rvPlaylists: RecyclerView
    lateinit var playListAdapter: PlaylistAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
        rvPlaylists = findViewById(R.id.rvPlaylist)
        rvPlaylists.layoutManager = LinearLayoutManager(this)
        playListAdapter = PlaylistAdapter(this, playlists)
        rvPlaylists.adapter = playListAdapter

        var mBotton = findViewById<FloatingActionButton>(R.id.btnCreate);
        mBotton.setOnClickListener(View.OnClickListener() {
            showBottomSheetDialog()
        })
        
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

    fun showBottomSheetDialog() {
        var bottomSheetDialog = BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.fragment_bottom);
        var createAuto = bottomSheetDialog.findViewById<LinearLayout>(R.id.createAuto);
        var createManual = bottomSheetDialog.findViewById<LinearLayout>(R.id.createManual);

        if (createAuto != null) {
            createAuto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this, "Create Auto", Toast.LENGTH_LONG).show();
                //ToDo:Start new fragment that shows a xml where user can enter appropriate fields

            })
        }
        if (createManual != null) {
            createManual.setOnClickListener(View.OnClickListener {
                Toast.makeText(this, "Create Manual", Toast.LENGTH_LONG).show();
                //ToDo: Start a new fragment that shows an xml where use can enter appropriate fields
                var manual = ManualPlaylistCreator()

            })
        }
        bottomSheetDialog.show();
    }


    companion object {
        const val TAG = "MainActivity2"
    }

}