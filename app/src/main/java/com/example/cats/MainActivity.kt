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
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var itemAdapter: CatsAdapter
    private val catsViewModel by viewModels<CatsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater).also { setContentView(it.root) }
        itemAdapter = CatsAdapter(this)

        binding.recyclerView.apply {
            adapter = itemAdapter
            layoutManager = GridLayoutManager(context, 2)
        }


        catsViewModel.items.observe(this, Observer {
            it ?: return@Observer
            itemAdapter.addItems(it)
        })
    }
}