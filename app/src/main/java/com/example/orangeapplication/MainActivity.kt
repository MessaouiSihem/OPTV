package com.example.orangeapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.content_layout.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initUI()

    }

    private fun initUI() {
        recycle_view.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
        }
    }
}
