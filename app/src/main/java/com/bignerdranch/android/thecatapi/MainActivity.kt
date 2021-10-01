package com.bignerdranch.android.thecatapi

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.bignerdranch.android.thecatapi.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var catAdapter: CatAdapter = CatAdapter()
    private val catViewModel by viewModels<CatViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recycler.apply {
            adapter = catAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        catViewModel.cats.observe(this, Observer {
            it ?: return@Observer
            catAdapter.addItems(it)
        })
    }
}