package com.ecommerce.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecommerce.databinding.ImageItemBinding

class ImgListAdapter(private val context : Context) : ListAdapter<String, ImgListAdapter.ViewHolder>(DataDiffCallBack()) {

    private class DataDiffCallBack : DiffUtil.ItemCallback<String>() {
        override fun areItemsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: String, newItem: String): Boolean =
            oldItem == newItem
    }

    /**
     * CREATING VIEW
     * FOR EACH ITEM
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ImageItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    /**
     * VIEW BINDER
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(context, getItem(position))
    }

    /**
     * VIEW HOLDER
     * SET DATA TO VIEWS
     */
    class ViewHolder(private val binding: ImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: String) {
            if(item.isNotEmpty()) {
                Glide.with(binding.image).load(item).into(binding.image)
            }
        }
    }
}