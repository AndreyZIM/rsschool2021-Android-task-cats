package com.example.cats.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cats.R
import com.example.cats.databinding.ItemCatBinding
import com.example.cats.model.Cat

class CatsAdapter() :
    PagingDataAdapter<Cat, CatsAdapter.CatsViewHolder>(IMAGE_COMPARATOR) {

    private val items = mutableListOf<Cat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(layoutInflater, parent, false)
        return CatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }
//
//    @SuppressLint("NotifyDataSetChanged")
//    fun addItems(newItems: List<Cat>) {
//        items.addAll(newItems)
//        notifyDataSetChanged()
//    }

    inner class CatsViewHolder(
        private val binding: ItemCatBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(cat: Cat) {
            binding.apply {
                Glide.with(itemView)
                    .load(cat.url)
                    .centerCrop()
                    .error(R.drawable.ic_baseline_error_24)
                    .into(imageCard)
            }

        }
    }

    companion object {
        private val IMAGE_COMPARATOR = object : DiffUtil.ItemCallback<Cat>() {
            override fun areItemsTheSame(oldItem: Cat, newItem: Cat): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Cat, newItem: Cat): Boolean {
                return oldItem == newItem
            }
        }
    }
}

