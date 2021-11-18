package com.ecommerce.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.databinding.CartItemBinding
import com.ecommerce.model.Cart

class CartListAdapter() : ListAdapter<Cart, CartListAdapter.ViewHolder>(DataDiffCallBack()) {

    private class DataDiffCallBack : DiffUtil.ItemCallback<Cart>() {
        override fun areItemsTheSame(oldItem: Cart, newItem: Cart): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Cart, newItem: Cart): Boolean =
            oldItem.name == newItem.name
    }

    /**
     * CREATING VIEW
     * FOR EACH ITEM
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CartItemBinding.inflate(
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
        holder.bind(getItem(position))
    }

    /**
     * VIEW HOLDER
     * SET DATA TO VIEWS
     */
    class ViewHolder(private val binding: CartItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Cart) {
            if (item.name.isNotEmpty()) {
                binding.product.text = item.name
                binding.price.text = item.price
            }
        }
    }
}