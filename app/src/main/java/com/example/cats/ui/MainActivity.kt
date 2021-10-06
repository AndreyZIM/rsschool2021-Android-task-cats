package com.example.cats.ui

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.os.Bundle
import android.transition.ChangeImageTransform
import android.transition.Explode
import android.util.Log
import android.util.Pair.create
import android.view.View
import android.view.Window
import android.widget.AdapterView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityOptionsCompat
import androidx.core.util.Pair
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cats.R
import com.example.cats.databinding.ActivityMainBinding
import com.example.cats.databinding.ItemCatBinding
import com.example.cats.model.Cat
import com.example.cats.model.CatsViewModel
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: CatsAdapter
    private lateinit var loadStateItemAdapter: CatsLoadingStateAdapter
    private val viewModel by viewModels<CatsViewModel>()
    private val gridLayoutManager = GridLayoutManager(this, 2)

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        itemAdapter = CatsAdapter(getItemClickListener())
        loadStateItemAdapter = CatsLoadingStateAdapter { itemAdapter.retry() }
        gridLayoutManager.spanSizeLookup = getSpanLookup()

        binding.recyclerView.apply {
            adapter = itemAdapter.withLoadStateFooter(
                loadStateItemAdapter
            )
            layoutManager = gridLayoutManager
        }
        binding.reconnectButton.setOnClickListener { getImages() }
        getImages()

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun getImages() {
        Log.i("myTag", "Tried to get images")
        if (isConnectedToInternet()) {
            binding.errorMessage.isVisible = false
            binding.progressMessage.isVisible = true
            binding.recyclerView.isVisible = true
            viewModel.cats.observe(this, Observer {
                it ?: return@Observer
                itemAdapter.submitData(this.lifecycle, it)
            })
        }
    }

    private fun getSpanLookup() = object : GridLayoutManager.SpanSizeLookup() {
        override fun getSpanSize(position: Int): Int {
            return if (position == itemAdapter.itemCount && loadStateItemAdapter.itemCount > 0) 2 else 1
        }
    }

    private fun getItemClickListener() = object : CatsAdapter.OnImageClickListener {
        override fun onItemClick(image: Cat, binding: ItemCatBinding, view: View) {
            val intent = Intent(this@MainActivity, DetailActivity::class.java)
            intent.putExtra(INTENT_PARAM_ID, image.url)
            val pair = Pair(view, TRANSITION_NAME)

            val option = ActivityOptionsCompat.makeSceneTransitionAnimation(this@MainActivity, pair)
            startActivity(intent, option.toBundle())
        }
        override fun hideLoadingBar() {
            binding.progressMessage.isVisible = false
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isConnectedToInternet(): Boolean {
        val connectivityManager = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        return connectivityManager.activeNetwork != null
    }

    companion object {
        const val INTENT_PARAM_ID = "id"
        const val TRANSITION_NAME = "transition"
    }
}