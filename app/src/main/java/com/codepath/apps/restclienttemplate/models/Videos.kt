package com.codepath.apps.restclienttemplate.models

import org.json.JSONArray
import org.json.JSONObject

class Videos {
    var id = ""
    var imageurl = ""
    var channelTitle = ""
    var description = ""
    companion object{
        fun fromJson(jsonObject: JSONObject): Videos{
            val vid = Videos()
            var snip = jsonObject.getJSONObject("snippet")
            var thumbnail = snip.getJSONObject("thumbnails").getJSONObject("default")
            vid.id = snip.getString("channelId")
            vid.channelTitle = snip.getString("channelTitle")
            vid.description = snip.getString("description")
            vid.imageurl = thumbnail.getString("url")
            return vid
        }
        fun fromJsonArray(jsonArray: JSONArray): List<Videos>{
            val vid = ArrayList<Videos>()
            for (i in 0 until jsonArray.length()){
                vid.add(fromJson(jsonArray.getJSONObject(i)))
            }
            return vid
        }
    }
}