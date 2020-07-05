package com.example.orangeapplication.ui.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.example.orangeapplication.R
import com.example.orangeapplication.data.model.Program
import com.example.orangeapplication.ui.adapter.ProgramAdapter
import com.example.orangeapplication.ui.viewmodel.ProgramViewModel
import com.example.orangeapplication.ui.viewmodel.base.ViewModelFactory
import com.example.orangeapplication.utils.Status
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_layout.*

class MainActivity : AppCompatActivity() {

    private lateinit var programViewModel: ProgramViewModel
    private lateinit var recycleAdapter: ProgramAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initUI()
        initViewModel()
        setUpObserver()
    }

    private fun initUI() {
        recycleAdapter = ProgramAdapter(arrayListOf())
        recycle_view.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            adapter = recycleAdapter
        }


        search_view.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                programViewModel?.let {
                    it.fetchPrograms("title%3D$query")
                    search_view.clearFocus()
                    return true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }

        })
    }

    private fun initViewModel() {
        programViewModel = ViewModelProviders.of(
            this, ViewModelFactory()
        ).get(ProgramViewModel::class.java)
    }


    private fun setUpObserver() {
        programViewModel.getPrograms().observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    showRecycleView(it.data)
                }
                Status.LOADING -> {
                    showProgress()
                }
                Status.ERROR -> {
                    showEmptyText()
                }
            }
        })
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
        recycle_view.visibility = View.GONE
        emty_text_view.visibility = View.GONE
    }

    private fun showEmptyText() {
        progressBar.visibility = View.GONE
        emty_text_view.visibility = View.VISIBLE
        recycle_view.visibility = View.GONE
    }

    private fun showRecycleView(data: Program?) {
        progressBar.visibility = View.GONE
        emty_text_view.visibility = View.GONE
        data?.let { programs ->
            if (programs.contents.isNullOrEmpty()) {
                showEmptyText()
            } else {
                renderList(programs)
            }
        }
    }

    private fun renderList(programs: Program) {
        recycleAdapter.removeData()
        recycleAdapter.addData(programs.contents)
        recycleAdapter.notifyDataSetChanged()
        recycle_view.visibility = View.VISIBLE
    }
}
