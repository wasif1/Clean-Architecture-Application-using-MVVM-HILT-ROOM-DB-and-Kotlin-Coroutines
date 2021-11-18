package com.ecommerce.presentation.fragment

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ecommerce.databinding.HomeBinding
import com.ecommerce.interfaces.ItemClick
import com.ecommerce.model.ProductsItem
import com.ecommerce.model.ResultData
import com.ecommerce.model.SubCategoriesItem
import com.ecommerce.presentation.adapter.CategoryAdapter
import com.ecommerce.presentation.adapter.ListAdapter
import com.ecommerce.presentation.viewmodel.ListViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class Home : Fragment(), ItemClick {

    /**
     * VARIABLE DECLARATIONS
     */
    private var sub_cat_id: String? = null
    private val viewModel: ListViewModel by viewModels()
    private lateinit var listAdapter: ListAdapter
    private lateinit var categoryAdapter: CategoryAdapter
    private var subCategories = ArrayList<SubCategoriesItem?>()
    private var sharedPreferences: SharedPreferences? = null
    private lateinit var binding: HomeBinding
    private var mLayoutManager: GridLayoutManager? = null
    private var loading = false
    var pastVisibleItems = 0
    var visibleItemCount: Int = 0
    var totalItemCount: Int = 0
    var products : ArrayList<ProductsItem>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = HomeBinding.inflate(layoutInflater)
        init()
        callApi()
        setListeners()
        return binding.root
    }

    /**
     * LISTENERS
     */
    private fun setListeners() {
        binding.refresh.setOnRefreshListener {
            products!!.clear()
            viewModel.listItems()
        }
        binding.recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy > 0) { //check for scroll down
                    visibleItemCount = mLayoutManager!!.childCount
                    totalItemCount = mLayoutManager!!.itemCount
                    pastVisibleItems = mLayoutManager!!.findFirstVisibleItemPosition()
                    if (!loading) {
                        if (visibleItemCount + pastVisibleItems >= totalItemCount) {
                            loading = true
                            Log.v("...", "Last Item Wow !")
                            viewModel.listItems()
                        }
                    }
                }
            }
        })
    }

    /**
     * INITIALISE THE ADAPTER
     * SET THE ADAPTER TO THE RECYCLER VIEW
     */
    private fun init() {
        products = ArrayList<ProductsItem>()
        mLayoutManager = GridLayoutManager(binding.recyclerView.context, 2)
        sharedPreferences = context?.getSharedPreferences("eCommercePrefs", Context.MODE_PRIVATE)
        listAdapter = sharedPreferences?.let { ListAdapter(requireContext(), it) }!!
        binding.recyclerView.apply {
            layoutManager = mLayoutManager
            adapter = listAdapter
        }
        categoryAdapter = CategoryAdapter(requireContext(), this)
        binding.category.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
    }

    /**
     * API CALL TO GET THE DATA
     * AND SET THE DATA INTO THE LIST
     */
    private fun callApi() {
        viewModel.listItems()
        activity?.let {
            viewModel.response.observe(it, {
                run {
                    when (it) {

                        is ResultData.Success -> {
                            loading = false
                            products!!.addAll(it.data?.data?.products!!)
                            if (products!!.size > 0) {
                                listAdapter.submitList(products)
                                binding.noRecord.noRecord.visibility = View.GONE
                                var editor = sharedPreferences!!.edit()
                                editor.putString(
                                    "deepLinkData",
                                    Gson().toJson(products!![0])
                                )
                                editor.apply()
                            } else {
                                binding.noRecord.noRecord.visibility = View.VISIBLE
                            }

                            if (it.data.data.subCategories != null && it.data.data.subCategories.size > 0) {
                                subCategories.clear()
                                var catAll = SubCategoriesItem()
                                catAll.subId = "All"
                                catAll.subName = "All"
                                catAll.subSlug = "All"
                                catAll.fkCatid = "All"
                                catAll.selected = true
                                subCategories.add(0, catAll)
                                subCategories.addAll(it.data.data.subCategories)
                                binding.category.visibility = View.VISIBLE
                            } else {
                                binding.category.visibility = View.GONE
                            }
                            categoryAdapter.submitList(subCategories)
                            binding.progress.visibility = View.GONE
                            binding.refresh.isRefreshing = false

                            listAdapter.notifyDataSetChanged()
                        }
                        is ResultData.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                        }
                        is ResultData.Error -> {
                            loading = false
                            Toast.makeText(context, it.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            })
        }
    }

    /**
     * API CALL TO GET THE CATEGORY DATA
     * AND SET THE DATA INTO THE LIST
     */
    override fun subCategoryData(item: SubCategoriesItem?) {
        sub_cat_id = item!!.subId
        if (!TextUtils.isEmpty(sub_cat_id) && !item.subName.equals("All")) {
            viewModel.catListItems(sub_cat_id!!)
            viewModel.response2.observe(this, {
                run {
                    when (it) {
                        is ResultData.Success -> {
                            listAdapter.submitList(it.data?.data?.products)
                            binding.progress.visibility = View.GONE
                            binding.refresh.isRefreshing = false
                        }
                        is ResultData.Loading -> {
                            binding.progress.visibility = View.VISIBLE
                        }
                        else -> {
                        }
                    }
                }
            })
        } else {
            viewModel.listItems()
        }

        for (i in subCategories.indices) {
            subCategories[i]!!.selected = subCategories[i]!! == item
        }
        categoryAdapter.notifyDataSetChanged()
    }
}