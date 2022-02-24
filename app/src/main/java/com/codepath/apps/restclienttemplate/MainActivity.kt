package com.codepath.apps.restclienttemplate
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton

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
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.adapters.PlaylistAdapter
import com.codepath.apps.restclienttemplate.models.Playlist
import com.google.android.gms.common.api.ApiException
import com.google.api.client.auth.oauth2.BearerToken
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.auth.oauth2.TokenResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    val playlists =  ArrayList<Playlist>()
    lateinit var rvPlaylists: RecyclerView
    lateinit var playListAdapter: PlaylistAdapter
     
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvPlaylists = findViewById(R.id.rvPlaylist)
        rvPlaylists.layoutManager = LinearLayoutManager(this)
        playListAdapter = PlaylistAdapter(playlists)
        rvPlaylists.adapter = playListAdapter
        var mBotton = findViewById<FloatingActionButton>(R.id.btnCreate);
        mBotton.setOnClickListener(View.OnClickListener() {
            showBottomSheetDialog()
        })

        val accessToken = intent.getStringExtra("accessToken")
        Log.i(TAG, "access token found from intent: " + accessToken)
        val tokenResponse = TokenResponse()
        tokenResponse.accessToken = accessToken
        val credential: Credential = createCredentialWithAccessTokenOnly(tokenResponse)
        val youtube = YouTube.Builder(NetHttpTransport(), JacksonFactory(), credential).build()

        val request: YouTube.Videos.List = youtube.videos()
            .list("snippet,contentDetails,statistics")

        val coroutineScope = MainScope()
        coroutineScope.launch {
            val defer = async(Dispatchers.IO) {
                request.setMyRating("like").execute()

            }
            Log.i("GettingChannels", defer.await().toString())
        }
    }

    private fun createCredentialWithAccessTokenOnly(tokenResponse: TokenResponse): Credential {
        return Credential(BearerToken.authorizationHeaderAccessMethod()).setFromTokenResponse(
            tokenResponse
        )
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
                Toast.makeText(this, "Create Auto", Toast.LENGTH_LONG).show();
            })
        }
        if (createManual != null) {
            createManual.setOnClickListener(View.OnClickListener {
                Toast.makeText(this, "Create Manual", Toast.LENGTH_LONG).show();
            })
        }
        bottomSheetDialog.show();
    }


    companion object {
        const val TAG = "MainActivity"
    }
    /*fun populatePlaylists(){
        
    }*/
}