package com.example.cats.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cats.R
import com.example.cats.databinding.ItemCatBinding
import com.example.cats.model.Cat

class CatsAdapter(private val listener: OnImageClickListener) :
    PagingDataAdapter<Cat, CatsAdapter.CatsViewHolder>(IMAGE_COMPARATOR) {

    lateinit var binding: ItemCatBinding
    private var flag = false

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        binding = ItemCatBinding.inflate(layoutInflater, parent, false)
        return CatsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        val currentItem = getItem(position)
        if (currentItem != null) {
            holder.bind(currentItem)
            if (!flag) {
                listener.hideLoadingBar()
                flag = true
            }
        }
    }

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
            binding.itemCard.setOnClickListener {
                listener.onItemClick(cat, binding, binding.itemCard)
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

    interface OnImageClickListener {
        fun onItemClick(image: Cat, binding: ItemCatBinding, view: View)
        fun hideLoadingBar()
    }
}
