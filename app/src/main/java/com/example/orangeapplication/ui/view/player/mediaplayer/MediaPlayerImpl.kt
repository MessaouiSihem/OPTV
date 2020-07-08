package com.example.orangeapplication.ui.view.player.mediaplayer

import android.content.Context
import android.net.Uri
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.source.dash.DashMediaSource
import com.google.android.exoplayer2.upstream.DataSource
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory
import com.google.android.exoplayer2.util.Util


class MediaPlayerImpl : MediaPlayer {

    private lateinit var exoPlalyer: SimpleExoPlayer
    private lateinit var context: Context

    override fun getPlayer(ctx: Context): ExoPlayer {
        context = ctx
        // Create a player instance.
        exoPlalyer = SimpleExoPlayer.Builder(context).build()
        return exoPlalyer
    }

    override fun preparePlayer(url: String) {

        // Create a data source factory.
        val dataSourceFactory: DataSource.Factory =
            DefaultHttpDataSourceFactory(Util.getUserAgent(context, "OrangeApplication"))

        // Create a HLS media source pointing to a playlist uri.
        val dashMediaSource: DashMediaSource  =
            DashMediaSource .Factory(dataSourceFactory).createMediaSource(Uri.parse(url))

        // Prepare the player with the media source.
        exoPlalyer.prepare(dashMediaSource)
        exoPlalyer.playWhenReady = true
    }

    override fun pausePlayer() {
        exoPlalyer.playWhenReady = false
    }

    override fun releasePlayer() {
        // release video to avoid memory leak..
        exoPlalyer.stop()
        exoPlalyer.release()
    }
}