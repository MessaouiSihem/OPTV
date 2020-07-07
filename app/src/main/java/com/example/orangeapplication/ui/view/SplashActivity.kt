package com.example.orangeapplication.ui.view

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import com.example.orangeapplication.R
import com.example.orangeapplication.ui.view.program.ProgramActivity
import kotlinx.android.synthetic.main.activity_splash.*


class SplashActivity : AppCompatActivity() {

    companion object {
        private const val SPLASH_TIME_OUT = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)


        val aniFade: Animation =
            AnimationUtils.loadAnimation(this, R.anim.fade_in)
        layout_splach.startAnimation(aniFade)

        Handler().postDelayed({
            //Do some stuff here, like implement deep linking
            startActivity(Intent(this, ProgramActivity::class.java))
            finish()
        }, SPLASH_TIME_OUT.toLong())
    }
}
