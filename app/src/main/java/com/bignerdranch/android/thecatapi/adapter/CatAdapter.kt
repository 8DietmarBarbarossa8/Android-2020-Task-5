package com.bignerdranch.android.thecatapi.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.bignerdranch.android.thecatapi.activities.ImageActivity
import com.bignerdranch.android.thecatapi.utils.Utils
import com.bignerdranch.android.thecatapi.R
import com.bignerdranch.android.thecatapi.models.Cat
import java.io.ByteArrayOutputStream
import java.lang.Exception

class CatAdapter : RecyclerView.Adapter<CatAdapter.ViewHolder>() {
    private val cats = arrayListOf<Cat>()
    private lateinit var context: Context

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
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
    fun addCats(newCats: List<Cat>) {
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
            titleTextView.text = catItem.id
            imageCatView.load(cat.url)
        }

        override fun onClick(v: View?) {
            try {
                val bitmap = (imageCatView.drawable as BitmapDrawable).bitmap
                val stream = ByteArrayOutputStream()
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
                val byteArray = stream.toByteArray()

                val intent = Intent(context, ImageActivity::class.java)
                intent.putExtra(Utils.IMAGE_KEY, byteArray)
                startActivity(context, intent, null)

                Utils.isMayExit = false
            } catch (e: Exception) {
                Toast.makeText(context, R.string.image_downloading_error, Toast.LENGTH_SHORT).show()
            }
        }
    }
}