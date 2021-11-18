package com.ecommerce.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.databinding.AddressItemBinding
import com.ecommerce.model.Address

class AddressListAdapter() :
    ListAdapter<Address, AddressListAdapter.ViewHolder>(DataDiffCallBack()) {

    private class DataDiffCallBack : DiffUtil.ItemCallback<Address>() {
        override fun areItemsTheSame(oldItem: Address, newItem: Address): Boolean =
            oldItem.name == newItem.name

        override fun areContentsTheSame(oldItem: Address, newItem: Address): Boolean =
            oldItem.name == newItem.name
    }

    /**
     * CREATING VIEW
     * FOR EACH ITEM
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            AddressItemBinding.inflate(
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
    class ViewHolder(private val binding: AddressItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Address) {
            if (item.name.isNotEmpty()) {
                binding.address.text = item.name
            }
        }
    }
}