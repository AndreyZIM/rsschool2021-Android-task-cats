package com.example.cats

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.GridLayout
import androidx.activity.viewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.bumptech.glide.Glide
import com.example.cats.databinding.ActivityMainBinding
import com.example.cats.model.Cat
import com.example.cats.ui.CatsAdapter
import com.example.cats.ui.CatsLoadingStateAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlin.random.Random


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: CatsAdapter
    private val viewModel by viewModels<CatsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        itemAdapter = CatsAdapter()

        binding.recyclerView.apply {
            adapter = itemAdapter.withLoadStateFooter(
                CatsLoadingStateAdapter { itemAdapter.retry() }
            )
            layoutManager = GridLayoutManager(context, 2)
        }

        viewModel.cats.observe(this, Observer {
            it ?: return@Observer
            itemAdapter.submitData(this.lifecycle, it)
        })
    }
}