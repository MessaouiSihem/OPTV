package com.example.orangeapplication.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.orangeapplication.R
import com.example.orangeapplication.data.model.program.Contents
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_program.view.*

class ProgramAdapter(private val contents: ArrayList<Contents>) :
    RecyclerView.Adapter<ProgramAdapter.DataViewHolder>() {

    var itemClick: ((Contents) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DataViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_program, parent,
                false
            )
        )

    override fun getItemCount(): Int = contents.size

    override fun onBindViewHolder(holder: DataViewHolder, position: Int) {
        holder.bind(contents[position])
        holder.itemView.setOnClickListener {
            itemClick?.invoke(contents[position])
        }
    }


    fun addData(list: List<Contents>) {
        contents.addAll(list)
    }

    fun removeData(){
        contents.clear()
    }


    class DataViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun bind(content: Contents) {
            itemView.title_program.text = content.title[0].value
            itemView.subtitle_program.text = content.subtitle

            Picasso.get()
                .load("http://statics.ocs.fr/" + content.imageurl)
                .into(itemView.image_program)
        }
    }
}