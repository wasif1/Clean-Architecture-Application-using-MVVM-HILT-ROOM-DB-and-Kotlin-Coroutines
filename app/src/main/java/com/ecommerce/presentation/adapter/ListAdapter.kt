package com.ecommerce.presentation.adapter

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.ecommerce.databinding.ProductItemBinding
import com.ecommerce.model.ProductsItem
import com.ecommerce.presentation.ProductDetails

class ListAdapter(private val context: Context, private val prefs: SharedPreferences) :
    ListAdapter<ProductsItem, com.ecommerce.presentation.adapter.ListAdapter.ViewHolder>(
        DataDiffCallBack()
    ) {


    private class DataDiffCallBack : DiffUtil.ItemCallback<ProductsItem>() {
        override fun areItemsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean =
            oldItem.prId == newItem.prId

        override fun areContentsTheSame(oldItem: ProductsItem, newItem: ProductsItem): Boolean =
            oldItem == newItem
    }

    /**
     * CREATING VIEW
     * FOR EACH ITEM
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ProductItemBinding.inflate(
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
        holder.bind(context, prefs, getItem(position))
    }

    /**
     * VIEW HOLDER
     * SET DATA TO VIEWS
     */
    class ViewHolder(private val binding: ProductItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, prefs: SharedPreferences, item: ProductsItem) {

            if (prefs.getBoolean("rtl", false)) {
                binding.productName.textDirection = View.TEXT_DIRECTION_RTL
                binding.brandName.textDirection = View.TEXT_DIRECTION_RTL
                binding.price.textDirection = View.TEXT_DIRECTION_RTL
            } else {
                binding.productName.textDirection = View.TEXT_DIRECTION_LTR
                binding.brandName.textDirection = View.TEXT_DIRECTION_LTR
                binding.price.textDirection = View.TEXT_DIRECTION_LTR
            }

            binding.productName.text = item.prName
            binding.brandName.text = item.brName
            binding.price.text = "AED. " + item.prPrice
            if (item.prImage!!.isNotEmpty()) {
                Glide.with(binding.thumbnail).load(item.prImage[0]).into(binding.thumbnail)
            }
            binding.root.setOnClickListener {
                context.startActivity(Intent(context, ProductDetails::class.java).apply {
                    putExtra("object", item)
                })
            }
        }
    }
}