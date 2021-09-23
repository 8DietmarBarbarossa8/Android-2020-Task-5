package com.bignerdranch.android.thecatapi

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewCatAdapter(private var catList: List<Cat>, private val context: Context) :
    RecyclerView.Adapter<RecyclerViewCatAdapter.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemLayoutView = LayoutInflater.from(context)
        val view = itemLayoutView.inflate(R.layout.list_item_cat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cat = catList[position]
        holder.bind(cat, context)
    }

    override fun getItemCount(): Int = catList.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var cat: Cat
        private var titleTextView: TextView = itemView.findViewById(R.id.cat_title)
        private var imageCatView: ImageView = itemView.findViewById(R.id.cat_image)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(catItem: Cat, context: Context){
            this.cat = catItem
            titleTextView.text = catItem.title
            imageCatView.setImageResource(catItem.imageURL)
        }

        override fun onClick(v: View?) {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra(ImageActivity.KEY, cat.imageURL)
            startActivity(context, intent, null)
        }
    }
}