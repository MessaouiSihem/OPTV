package com.example.orangeapplication.viewmodel

import androidx.lifecycle.ViewModel
import com.example.orangeapplication.ui.view.player.mediaplayer.MediaPlayerImpl

class PlayerViewModel : ViewModel() {

    private var mediaPlayerImpl: MediaPlayerImpl = MediaPlayerImpl()

    fun getMediaPlayer() = mediaPlayerImpl

    fun playVideo(url: String) {
        mediaPlayerImpl.preparePlayer(url)
    }

    fun pauseVideo(){
        mediaPlayerImpl.pausePlayer()
    }

    fun releaseVideo() {
        mediaPlayerImpl.releasePlayer()
    }
}