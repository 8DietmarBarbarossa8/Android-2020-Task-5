package com.bignerdranch.android.thecatapi

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bignerdranch.android.thecatapi.model.Cat

class CatAdapter : RecyclerView.Adapter<CatAdapter.ViewHolder>() {
    private val cats = mutableListOf<Cat>()
    private lateinit var context: Context

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemLayoutView = LayoutInflater.from(parent.context)
        context = parent.context
        val view = itemLayoutView.inflate(R.layout.list_item_cat, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val cat = cats[position]
        holder.bind(cat)
    }

    override fun getItemCount(): Int = cats.size

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(newCats: List<Cat>) {
        cats.addAll(newCats)
        notifyDataSetChanged()
    }

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        private lateinit var cat: Cat
        private var titleTextView: TextView = itemView.findViewById(R.id.cat_title)
        private var imageCatView: ImageView = itemView.findViewById(R.id.cat_image)

        init {
            itemView.setOnClickListener(this)
        }

        fun bind(catItem: Cat) {
            this.cat = catItem
            titleTextView.text = catItem.description
            imageCatView.load(cat.url)
        }

        override fun onClick(v: View?) {
            val intent = Intent(context, ImageActivity::class.java)
            intent.putExtra(ImageActivity.KEY, cat.url)
            startActivity(context, intent, null)
        }
    }
}