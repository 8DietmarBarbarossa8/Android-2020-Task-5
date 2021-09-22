package com.bignerdranch.android.thecatapi

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewCatAdapter(private var catList: List<Cat>, private val context: Context) :
    RecyclerView.Adapter<RecyclerViewCatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemLayoutView =
            LayoutInflater.from(context).inflate(R.layout.list_item_cat, null)
        return ViewHolder(itemLayoutView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(catList[position], context)
    }

    override fun getItemCount(): Int = catList.size

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView = itemView.findViewById(R.id.cat_title)
        var imageCatView: ImageView = itemView.findViewById(R.id.cat_image)

        fun bind(catItem: Cat, context: Context){
            titleTextView.text = catItem.title
            imageCatView.setImageResource(catItem.imageURL)
        }
    }
}