package Playcom.codeparth.apps.restclienttemplate

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.codepath.apps.restclienttemplate.R
import com.codepath.apps.restclienttemplate.adapters.PlaylistAdapter
import com.codepath.apps.restclienttemplate.models.Playlist

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
        
    }
    /*fun populatePlaylists(){
        
    }*/
}