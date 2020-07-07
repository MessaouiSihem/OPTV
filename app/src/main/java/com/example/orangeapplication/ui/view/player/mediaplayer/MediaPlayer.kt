package com.example.orangeapplication.ui.view.player.mediaplayer

import android.content.Context
import com.google.android.exoplayer2.ExoPlayer

interface MediaPlayer {

    fun getPlayer(context: Context): ExoPlayer
    fun preparePlayer(url: String)
    fun pausePlayer()
    fun releasePlayer()
}