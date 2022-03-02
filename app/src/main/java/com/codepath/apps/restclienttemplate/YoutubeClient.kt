package com.codepath.apps.restclienttemplate

import android.app.Application
import android.util.Log
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler
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

class YoutubeClient(accessToken: String){
    private val youtube: YouTube
    init {
        val tokenResponse = TokenResponse()
        tokenResponse.accessToken = accessToken
        val credential: Credential = createCredentialWithAccessTokenOnly(tokenResponse)
        youtube = YouTube.Builder(NetHttpTransport(), JacksonFactory(), credential).build()
        
    }
    private fun createCredentialWithAccessTokenOnly(tokenResponse: TokenResponse): Credential {
        return Credential(BearerToken.authorizationHeaderAccessMethod()).setFromTokenResponse(
            tokenResponse
        )
    }

    fun getLikedVideos(handler: JsonHttpResponseHandler) {
        val request = youtube.videos().list("snippet,contentDetails,statistics")

        val coroutineScope = MainScope()
        coroutineScope.launch {
            val defer = async(Dispatchers.IO) {
                val response = request.setMyRating("like").execute()
                handler.onResponse(this, response.toString())
            }
        }
    }
}

