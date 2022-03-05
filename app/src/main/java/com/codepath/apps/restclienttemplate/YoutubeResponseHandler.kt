package com.codepath.apps.restclienttemplate

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler.JSON
import com.google.api.client.json.GenericJson
import okhttp3.Call
import okhttp3.Headers
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import org.json.JSONTokener
import java.io.IOException

abstract class YoutubeResponseHandler<T: com.google.api.client.json.GenericJson>{
    abstract fun onSuccess(json: T)
    abstract fun onFailure(
        response: T
    )

    inner class JSON {
        var jsonObject: JSONObject? = null
        var jsonArray: JSONArray? = null
        override fun toString(): String {
            return String.format("jsonObject=%s, jsonArray=%s", jsonObject, jsonArray)
        }
    }

    @Throws(IOException::class)
    fun onResponse(response: T) {
        onSuccess(response)
    }

    companion object {
        private const val INTERNAL_ERROR = 500
    }
}