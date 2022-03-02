package com.codepath.apps.restclienttemplate.models
import org.json.JSONArray
import org.json.JSONObject

class Playlist {
    var title:String  = ""
    var description:String = ""
    var id:String = ""
    companion object{
        fun fromJsonItem(jsonObject: JSONObject): Playlist{
            val playlist = Playlist()
            val snip = jsonObject.getJSONObject("snippet")
            playlist.title = snip.getString("title")
            playlist.description = snip.getString("description")
            playlist.id = jsonObject.getString("id")
            return playlist
        }
        fun ParseJsonArray(jsonArray: JSONArray): List<Playlist>{
            val playlists = ArrayList<Playlist>()
            for (i in 0 until jsonArray.length()){
                playlists.add(fromJsonItem(jsonArray.getJSONObject(i)))
            }
            return playlists
        }
    }
}