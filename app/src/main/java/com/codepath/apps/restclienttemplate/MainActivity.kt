package com.codepath.apps.restclienttemplate
import android.content.Intent
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

import com.google.api.services.youtube.model.Playlist
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.codepath.apps.restclienttemplate.adapters.PlaylistAdapter
import com.google.api.services.youtube.model.PlaylistListResponse
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val playlists =  ArrayList<Playlist>()
    lateinit var rvPlaylists: RecyclerView
    lateinit var playListAdapter: PlaylistAdapter
    lateinit var swipeContainer : SwipeRefreshLayout
    val client = RestApplication.getYoutubeClient(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val accessToken = intent.getStringExtra("accessToken")
        Log.i(TAG, "access token found from intent: " + accessToken)
        setContentView(R.layout.activity_main)
        swipeContainer = findViewById(R.id.swipeContainer)
        swipeContainer.setOnRefreshListener {
            Log.i(TAG, "refreshing Timeline")
            populatePlaylists()
        }
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light,
            android.R.color.holo_red_light)
        rvPlaylists = findViewById(R.id.rvPlaylists)
        rvPlaylists.layoutManager = LinearLayoutManager(this)
        playListAdapter = PlaylistAdapter(this, playlists)
        rvPlaylists.adapter = playListAdapter
        var mBotton = findViewById<FloatingActionButton>(R.id.btnCreate);
        mBotton.setOnClickListener(View.OnClickListener() {
            showBottomSheetDialog()
        })
        client.getUserPlaylists(object : YoutubeResponseHandler<PlaylistListResponse>() {
            override fun onSuccess(json: PlaylistListResponse) {
                playlists.addAll(json.items)
                playListAdapter.notifyDataSetChanged()
            }
            override fun onFailure(response: PlaylistListResponse) {
                Log.i(TAG, "Error")
            }
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
        val bottomSheetDialog = BottomSheetDialog(this);
        bottomSheetDialog.setContentView(R.layout.fragment_bottom);
        val createAuto = bottomSheetDialog.findViewById<LinearLayout>(R.id.createAuto);
        val createManual = bottomSheetDialog.findViewById<LinearLayout>(R.id.createManual);

        if (createAuto != null) {
            createAuto.setOnClickListener(View.OnClickListener {
                Toast.makeText(this, "Create Auto", Toast.LENGTH_LONG).show()

                val intent = Intent(this, AutomaticPlaylistCreator::class.java)
                startActivity(intent)

                bottomSheetDialog.dismiss()
            })
        }
        if (createManual != null) {
            createManual.setOnClickListener(View.OnClickListener {
                Toast.makeText(this, "Create Manual", Toast.LENGTH_LONG).show()

                val intent = Intent(this, ManualPlaylistCreator::class.java)
                startActivity(intent)
                bottomSheetDialog.dismiss()
            })
        }
        bottomSheetDialog.show();
    }

    fun populatePlaylists()
    {
        val accessToken = intent.getStringExtra("accessToken")
        playListAdapter.clear()
        client.getUserPlaylists(object : YoutubeResponseHandler<PlaylistListResponse>() {
            override fun onSuccess(json: PlaylistListResponse) {
                playlists.addAll(json.items)
                playListAdapter.notifyDataSetChanged()
                swipeContainer.isRefreshing = false
            }
            override fun onFailure(response: PlaylistListResponse) {
                Log.i(TAG, "Error")
            }
        })
    }


    companion object {
        const val TAG = "MainActivity"
    }
    /*fun populatePlaylists(){
        
    }*/
}