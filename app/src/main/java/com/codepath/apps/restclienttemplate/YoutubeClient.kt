package com.codepath.apps.restclienttemplate

import com.google.api.client.auth.oauth2.BearerToken
import com.google.api.client.auth.oauth2.Credential
import com.google.api.client.auth.oauth2.TokenResponse
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.jackson2.JacksonFactory
import com.google.api.services.youtube.YouTube
import com.google.api.services.youtube.model.*
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

    fun getLikedVideos(handler: YoutubeResponseHandler<VideoListResponse>) {
        val request: YouTube.Videos.List = youtube.videos().list("snippet,contentDetails,statistics")
        val coroutineScope = MainScope()
        coroutineScope.launch {
            val defer = async(Dispatchers.IO) {
                request.setMyRating("like").execute()
            }
            handler.onResponse(defer.await())
        }
    }

    fun getUserPlaylists(handler: YoutubeResponseHandler<PlaylistListResponse>) {
        val request = youtube.playlists().list("snippet,contentDetails")
        val coroutineScope = MainScope()
        coroutineScope.launch {
            val defer = async(Dispatchers.IO) {
                request.setMaxResults(10L).setMine(true).execute()
            }
            handler.onResponse(defer.await())
        }
    }


    fun createManualPlaylistNoHandler(title: String, desc : String) {
        val playlist = Playlist()
        val snippet = PlaylistSnippet()
        val status = PlaylistStatus()
        status.privacyStatus = "public"
        snippet.description = desc
        snippet.title = title
        playlist.snippet = snippet
        playlist.status = status
        val request = youtube.playlists().insert("snippet, status", playlist)
        val coroutineScope = MainScope()
        coroutineScope.launch {
            val defer = async(Dispatchers.IO) {
                request.execute()
            }
        }
    }

    fun createManualPlaylist(title: String, desc : String, handler: YoutubeResponseHandler<Playlist>) {
        val playlist = Playlist()
        val snippet = PlaylistSnippet()
        val status = PlaylistStatus()
        status.privacyStatus = "public"
        snippet.description = desc
        snippet.title = title
        playlist.status = status
        playlist.snippet = snippet
        val request = youtube.playlists().insert("snippet, status", playlist)
        val coroutineScope = MainScope()
        coroutineScope.launch {
            val defer = async(Dispatchers.IO) {
                request.execute()
            }
            handler.onResponse(defer.await())
        }
    }

    fun getSearchResult( keyword: String, handler: YoutubeResponseHandler<SearchListResponse>) {
        val request = youtube.search().list("snippet")
        val coroutineScope = MainScope()
        coroutineScope.launch {
            val defer = async(Dispatchers.IO) {
                request.setMaxResults(25L).setQ(keyword).setType("video").execute()
            }
            handler.onResponse(defer.await())
        }
    }

    fun addVideoToPlaylist(playlistId: String, videoId: String) {
        val playlistItem = PlaylistItem()
        val snippet = PlaylistItemSnippet()
        snippet.playlistId = playlistId
        snippet.position = 0L
        val resourceId = ResourceId()
        resourceId.kind = "youtube#video"
        resourceId.videoId = videoId
        snippet.resourceId = resourceId
        playlistItem.snippet = snippet

        val request = youtube.playlistItems().insert("snippet", playlistItem)
        val coroutineScope = MainScope()
        coroutineScope.launch {
            val defer = async(Dispatchers.IO) {
                request.execute()
            }
        }
    }

    fun deletePlaylist(playlistId: String){
        val request = youtube.playlists().delete(playlistId)    

        val coroutineScope = MainScope()
        coroutineScope.launch {
            val defer = async(Dispatchers.IO) {
                request.execute()
            }
        }
        

    }




}

