package com.codepath.apps.restclienttemplate

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast


class ManualPlaylistCreator:  Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.creator_playlist_manual, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //Set onClickListeners and Handle Logic

        view.findViewById<Button>(R.id.btnConfirm).setOnClickListener {
            Toast.makeText(requireContext(), "Making Playlist...", Toast.LENGTH_SHORT).show()
            
            //Create the playlist with the name (title) and description

            //TODO: get title and description

            //TODO: use YT API to create playlist
        }

        view.findViewById<Button>(R.id.btnClear).setOnClickListener {
            Toast.makeText(requireContext(), "Clearing Title and Description",Toast.LENGTH_SHORT).show()
        }
    }

    companion object {

    }
}