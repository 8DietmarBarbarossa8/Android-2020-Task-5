package com.bignerdranch.android.thecatapi

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.thecatapi.adapter.CatAdapter
import com.bignerdranch.android.thecatapi.databinding.ActivityMainBinding
import com.bignerdranch.android.thecatapi.models.CatViewModel
import java.lang.Exception


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var catAdapter: CatAdapter = CatAdapter()
    // it's necessary for storing catLists
    private val catViewModel by viewModels<CatViewModel>()
    // influence how much cats need to add
    private var coefficient = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialise recyclerview
        binding.recycler.apply {
            adapter = catAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // download start list
        downloadCats(catViewModel, false)

        // handling the situation when reaching the end of the list
        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (isVisibleLastCatFully())
                    binding.floatingActionButton.isVisible = true
            }
        })

        // add new cats in list
        binding.floatingActionButton.setOnClickListener {
            try {
                if (coefficient < 100) coefficient++

                for (i in 1..coefficient)
                    downloadCats(CatViewModel(), true)
            } catch (e: Exception) {
                Toast.makeText(this, R.string.overdose, Toast.LENGTH_SHORT).show()
            }

            binding.floatingActionButton.isVisible = false
        }
    }

    // add list in viewModel and adapter
    private fun downloadCats(catViewModel: CatViewModel, isNeedMoreCats: Boolean) {
        catViewModel.cats.observe(this, Observer {
            it ?: return@Observer
            if (isNeedMoreCats) this.catViewModel.updateListCats(it)
            catAdapter.addCats(it)
        })
    }

    // check, if this cat is last
    private fun isVisibleLastCatFully(): Boolean {
        val layoutManager = binding.recycler.layoutManager as LinearLayoutManager
        val pos = layoutManager.findLastCompletelyVisibleItemPosition()
        val numItems = binding.recycler.adapter?.itemCount ?: 0
        return pos >= numItems - 1
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putInt(COEFFICIENT_KEY, coefficient)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        coefficient = savedInstanceState.getInt(COEFFICIENT_KEY)
        Log.d("TAG", "Restored!")
    }

    companion object {
        const val COEFFICIENT_KEY = "32FA43223L5**&*"
    }
}