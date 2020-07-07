package com.example.orangeapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orangeapplication.R
import com.example.orangeapplication.data.model.detail.Seasons
import com.example.orangeapplication.data.model.program.Contents
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_season.view.*


class SeasonAdapter(private val seasons: ArrayList<Seasons>) :
    RecyclerView.Adapter<SeasonAdapter.DataViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
       DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_season, parent,
                false
            )
        )


    override fun getItemCount(): Int = seasons.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(seasons[position])
    }


    fun addData(list: List<Seasons>) {
        seasons.addAll(list)
    }


    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(season: Seasons) {
            itemView.title_saison.text = season.subtitle
            itemView.number_saison.text = "Saison N° " + season.number
           /* Picasso.get()
                .load("http://statics.ocs.fr/" + season.imageurl)
                .into(itemView.image_saison)*/
        }
    }


}