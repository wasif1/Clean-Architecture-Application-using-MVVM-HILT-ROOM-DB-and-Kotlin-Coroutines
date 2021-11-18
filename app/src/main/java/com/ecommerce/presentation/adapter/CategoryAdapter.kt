package com.ecommerce.presentation.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.R
import com.ecommerce.databinding.CategoryItemBinding
import com.ecommerce.interfaces.ItemClick
import com.ecommerce.model.SubCategoriesItem

class CategoryAdapter(private val context : Context, private val listener : ItemClick) : ListAdapter<SubCategoriesItem, CategoryAdapter.ViewHolder>(DataDiffCallBack()) {

    private class DataDiffCallBack : DiffUtil.ItemCallback<SubCategoriesItem>() {
        override fun areItemsTheSame(oldItem: SubCategoriesItem, newItem: SubCategoriesItem): Boolean =
            oldItem.subId == newItem.subId

        override fun areContentsTheSame(oldItem: SubCategoriesItem, newItem: SubCategoriesItem): Boolean =
            oldItem == newItem
    }

    /**
     * CREATING VIEW
     * FOR EACH ITEM
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            CategoryItemBinding.inflate(
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
        holder.bind(context, getItem(position), listener)
    }

    /**
     * VIEW HOLDER
     * SET DATA TO VIEWS
     */
    class ViewHolder(private val binding: CategoryItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(context: Context, item: SubCategoriesItem, listener : ItemClick) {
            binding.name.text = item.subName
            if (item.selected) {
                binding.buttonMain.background = context.resources.getDrawable(R.drawable.button_fill)
                binding.name.setTextColor(context.getResources().getColor(R.color.front_color))
            } else {
                binding.buttonMain.background =context.resources.getDrawable(R.drawable.button)
                binding.name.setTextColor(context.getResources().getColor(R.color.theme))
            }
            binding.root.setOnClickListener {
                listener.subCategoryData(item)
            }
        }
    }
}