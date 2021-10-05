package com.bignerdranch.android.thecatapi.activities

import android.content.Intent
import android.content.Intent.makeRestartActivityTask
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bignerdranch.android.thecatapi.R
import com.bignerdranch.android.thecatapi.adapter.CatAdapter
import com.bignerdranch.android.thecatapi.databinding.ActivityMainBinding
import com.bignerdranch.android.thecatapi.models.CatViewModel
import com.bignerdranch.android.thecatapi.utils.Utils
import com.orangegangsters.github.swipyrefreshlayout.library.SwipyRefreshLayoutDirection
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    // it's necessary for storing catLists
    private val catViewModel by viewModels<CatViewModel>()
    private var catAdapter: CatAdapter = CatAdapter()

    private var wasDownloadedPortionYet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // initialise recyclerview
        binding.recycler.apply {
            adapter = catAdapter
            layoutManager = LinearLayoutManager(this@MainActivity)
        }

        // set swipe refresh direction
        binding.refreshListLayout.direction =
            when {
                !isOnline() -> {
                    // show message, if hasn't internet connection
                    binding.noInternetConnectionMessage.visibility = View.VISIBLE

                    SwipyRefreshLayoutDirection.TOP
                }
                else -> SwipyRefreshLayoutDirection.BOTTOM
            }

        // give possibility app to search list on weak devices
        binding.recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                binding.noInternetConnectionMessage.visibility = View.GONE
                binding.refreshListLayout.direction = SwipyRefreshLayoutDirection.BOTTOM

                wasDownloadedPortionYet = false
            }
        })

        // download start list
        downloadCats(catViewModel, false)

        // add new cats in list or restart app, if no internet connection
        binding.refreshListLayout.setOnRefreshListener {
            if (binding.refreshListLayout.direction == SwipyRefreshLayoutDirection.TOP)
                restartApp()
            else if (!wasDownloadedPortionYet){
                try {
                    if (Utils.countOfPortions < 5) Utils.countOfPortions++

                    for (i in 1..Utils.countOfPortions)
                        downloadCats(CatViewModel(), true)

                    Toast.makeText(this, R.string.pagination, Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                    Toast.makeText(this, R.string.overdose_error, Toast.LENGTH_SHORT).show()
                }

                wasDownloadedPortionYet = true
            }

            binding.refreshListLayout.isRefreshing = false
        }
    }

    private fun isOnline(): Boolean {
        return try {
            val p1 = Runtime.getRuntime().exec("ping -c 1 www.google.com")
            p1.waitFor() == 0
        } catch (e: Exception) {
            false
        }
    }

    private fun restartApp(){
        val packageManager: PackageManager = packageManager
        val intent = packageManager.getLaunchIntentForPackage(packageName)
        val componentName = intent?.component
        val mainIntent: Intent = makeRestartActivityTask(componentName)
        startActivity(mainIntent)
        exitProcess(0)
    }

    // add list in viewModel and adapter
    private fun downloadCats(catViewModel: CatViewModel, isNeedMoreCats: Boolean) {
        catViewModel.cats.observe(this, Observer {
            it ?: return@Observer
            if (isNeedMoreCats) this.catViewModel.updateListCats(it)
            catAdapter.addCats(it)
        })
    }

    // ask about exit
    override fun onBackPressed() {
        if (Utils.isMayExit)
            super.onBackPressed()
        else {
            Toast.makeText(this, R.string.exit_notification, Toast.LENGTH_SHORT).show()
            Utils.isMayExit = true
        }
    }
}