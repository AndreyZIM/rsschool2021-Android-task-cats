package com.example.cats.ui

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.cats.R
import com.example.cats.databinding.ItemCatBinding
import com.example.cats.model.Cat

class CatsAdapter(private val context: Context) : RecyclerView.Adapter<CatsViewHolder>() {

    private val items = mutableListOf<Cat>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatsViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCatBinding.inflate(layoutInflater, parent, false)
        return CatsViewHolder(binding, context)
    }

    override fun onBindViewHolder(holder: CatsViewHolder, position: Int) {
        val imageUrl = items[position].imageUrl ?: ""
        holder.bind(imageUrl)
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addItems(newItems: List<Cat>) {
        items.addAll(newItems)
        notifyDataSetChanged()
    }
}

class CatsViewHolder(
    private val binding: ItemCatBinding,
    private val context: Context
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(imageUrl: String) {
        Glide.with(context)
            .load(imageUrl)
            .centerCrop()
            .error(R.drawable.ic_baseline_error_24)
            .into(binding.image)

    }

}