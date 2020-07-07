package com.example.orangeapplication.ui.view.program

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager

import com.example.orangeapplication.R
import com.example.orangeapplication.data.model.program.Contents
import com.example.orangeapplication.data.model.program.Program
import com.example.orangeapplication.ui.adapter.ProgramAdapter
import com.example.orangeapplication.utils.Status
import com.example.orangeapplication.viewmodel.ProgramViewModel
import com.example.orangeapplication.viewmodel.base.ViewModelFactory
import kotlinx.android.synthetic.main.content_layout.*
import kotlinx.android.synthetic.main.fragment_programs.*


class ProgramsFragment : Fragment() {

    private lateinit var programViewModel: ProgramViewModel
    private lateinit var recycleAdapter: ProgramAdapter
    private val KEYWORD_PREFIX = "title%3D"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_programs, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initUI()
        initViewModel()
        setUpObserver()
    }

    private fun initUI() {
        recycleAdapter = ProgramAdapter(arrayListOf())
        recycle_view.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = recycleAdapter.apply {
                itemClick = {
                    openDetailProgram(it)
                }
            }
        }

        search_view.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                programViewModel?.let {
                    it.fetchPrograms(KEYWORD_PREFIX + "$query")
                    search_view.clearFocus()
                    return true
                }
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })

        // set default query text
        search_view.setQuery("Amour", false)
    }

    private fun openDetailProgram(content: Contents) {
        findNavController().navigate(
            R.id.action_fragment_programs_to_fragment_detail_program,
            bundleOf("program" to content)
        )
    }


    private fun initViewModel() {
        programViewModel = ViewModelProviders.of(
            requireActivity(), ViewModelFactory()
        ).get(ProgramViewModel::class.java)
    }


    private fun setUpObserver() {
        programViewModel.getPrograms().observe(viewLifecycleOwner, Observer {
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
