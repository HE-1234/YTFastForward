package com.codepath.apps.restclienttemplate.models
import org.json.JSONArray
import org.json.JSONObject

class Playlist {
    var title:String  = ""
    var description:String = ""
    var id:Int = 0
    companion object{
        fun fromJson(jsonObject: JSONObject): Playlist{
            return Playlist()
        }
    }
}