package com.bignerdranch.android.thecatapi

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity(){
    private lateinit var adapter: RecyclerViewCatAdapter
    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler)
        recyclerView.layoutManager = LinearLayoutManager(this)
        adapter = RecyclerViewCatAdapter(listOf(
            Cat(0, "CAt1", R.drawable.cat),
            Cat(1, "CAT@", R.drawable.cat2),
            Cat(2, "CAT@3", R.drawable.cat3),
            Cat(3, "CAT@4", R.drawable.cat4),
            Cat(4, "CAT@FIVE", R.drawable.cat5),
            Cat(5, "CAT6", R.drawable.cat6),
            Cat(6, "CAT7", R.drawable.cat7),
            Cat(7, "CAT8", R.drawable.cat8),
            Cat(8, "CATNEIN", R.drawable.cat9),
            Cat(9, "Asya", R.drawable.cat10)
        ), this)
        recyclerView.adapter = adapter
    }
}