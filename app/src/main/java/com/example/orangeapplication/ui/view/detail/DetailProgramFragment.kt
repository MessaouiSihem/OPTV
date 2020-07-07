package com.example.orangeapplication.ui.view.detail

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.orangeapplication.R
import com.example.orangeapplication.data.model.detail.DetailProgram
import com.example.orangeapplication.data.model.program.Contents
import com.example.orangeapplication.ui.adapter.SeasonAdapter
import com.example.orangeapplication.ui.view.player.PlayerActivity
import com.example.orangeapplication.utils.Status
import com.example.orangeapplication.viewmodel.DetailProgramViewModel
import com.example.orangeapplication.viewmodel.base.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_program.*


class DetailProgramFragment : Fragment() {

    private var content: Contents? = null
    private lateinit var detailProgramViewModel: DetailProgramViewModel
    private lateinit var recycleAdapter: SeasonAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_program, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewModel()

        content = arguments?.getParcelable("program")
        content?.let {
            detailProgramViewModel.fetchDetail(it.detaillink)
            initUI(it)
        }
    }

    private fun initViewModel() {
        detailProgramViewModel = ViewModelProviders.of(
            requireActivity(), ViewModelFactory()
        ).get(DetailProgramViewModel::class.java)
        setUpObserver()
    }

    private fun initUI(content_detail: Contents) {
        title.text = content_detail.title[0].value
        subtitle_program.text = content_detail.subtitle
        Picasso.get()
            .load("http://statics.ocs.fr/" + content_detail.fullscreenimageurl)
            .into(image_fullscreen)

        button_play.setOnClickListener {
            activity?.startActivity(Intent(activity, PlayerActivity::class.java))
        }

        recycleAdapter = SeasonAdapter(arrayListOf())
        season_recycle_view.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = recycleAdapter
        }
    }

    private fun setUpObserver() {
        detailProgramViewModel.getDetail().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    if (viewLifecycleOwner.lifecycle.currentState == Lifecycle.State.RESUMED) {
                        hideProgress()
                        setUpPitch(it.data)
                    }
                }
                Status.LOADING -> {
                    showProgress()
                }
                Status.ERROR -> {
                    hideProgress()
                }
            }
        })
    }

    private fun setUpPitch(data: DetailProgram?) {

        data?.let {
            Log.e("TATATA", it.contents.toString())
            // For pitch
            if (!it.contents.pitch.isNullOrEmpty())
                pitch.text = it.contents.pitch
            else if (!it.contents.seasons.isNullOrEmpty())
                pitch.text = it.contents.seasons[0].pitch
            else pitch.text = resources.getString(R.string.empty_pitch)

            // for Seasons
            if (!it.contents.seasons.isNullOrEmpty()) {
                recycleAdapter.addData(it.contents.seasons)
                recycleAdapter.notifyDataSetChanged()
                season_recycle_view.visibility = View.VISIBLE
            }
        }

    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress() {
        progressBar.visibility = View.GONE
    }
}
