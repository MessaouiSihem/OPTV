package com.example.orangeapplication.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders

import com.example.orangeapplication.R
import com.example.orangeapplication.data.model.detail.DetailProgram
import com.example.orangeapplication.data.model.program.Contents
import com.example.orangeapplication.utils.Status
import com.example.orangeapplication.viewmodel.DetailProgramViewModel
import com.example.orangeapplication.viewmodel.base.ViewModelFactory
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.fragment_detail_program.*


class DetailProgramFragment : Fragment() {

    private var content: Contents? = null
    private lateinit var detailProgramViewModel: DetailProgramViewModel

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
        }
    }

    private fun setUpObserver() {
        detailProgramViewModel.getDetail().observe(viewLifecycleOwner, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    hideProgress()
                    setUpPitch(it.data)
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
            if (!it.contents.pitch.isNullOrEmpty())
                pitch.text = it.contents.pitch
            else if (!it.contents.seasons.isNullOrEmpty())
                pitch.text = it.contents.seasons[0].pitch
            else pitch.text = resources.getString(R.string.empty_pitch)
        }
    }

    private fun showProgress() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgress(){
        progressBar.visibility= View.GONE
    }
}
