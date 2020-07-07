package com.example.orangeapplication.ui.view.player

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProviders
import com.example.orangeapplication.R
import com.example.orangeapplication.viewmodel.PlayerViewModel
import com.example.orangeapplication.viewmodel.base.ViewModelFactory
import com.google.android.exoplayer2.ExoPlayer
import kotlinx.android.synthetic.main.activity_player.*

class PlayerActivity : AppCompatActivity() {

    private lateinit var exoPlayer: ExoPlayer
    private lateinit var playerViewModel: PlayerViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // enableImmersiveMode()
        setContentView(R.layout.activity_player)

        initViewModel()
        initialisePlayer()
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) enableImmersiveMode()
    }

    private fun enableImmersiveMode() {
        window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                // Hide the nav bar and status bar
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
    }

    private fun initViewModel() {
        playerViewModel = ViewModelProviders.of(
            this, ViewModelFactory()
        ).get(PlayerViewModel::class.java)
    }

    private fun initialisePlayer() {
        // Get player instance and attaching the player to a view
        exoPlayer = playerViewModel.getMediaPlayer().getPlayer(this)
        playerView.player = exoPlayer
        playerViewModel.playVideo("https://bitmovin-a.akamaihd.net/content/MI201109210084_1/mpds/f08e80da-bf1d-4e3d-8899-f0f6155f6efa.mpd")
    }

    override fun onPause() {
        super.onPause()
        playerViewModel.pauseVideo()
    }

    override fun onDestroy() {
        super.onDestroy()
        playerViewModel.releaseVideo()
    }

}
