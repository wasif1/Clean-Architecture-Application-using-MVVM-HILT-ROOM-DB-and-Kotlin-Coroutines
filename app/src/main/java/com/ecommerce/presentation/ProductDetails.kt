package com.ecommerce.presentation

import android.R
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.SnapHelper
import com.ecommerce.Base
import com.ecommerce.databinding.ActivityProductDetailsBinding
import com.ecommerce.model.Cart
import com.ecommerce.model.ProductsItem
import com.ecommerce.presentation.adapter.ImgListAdapter
import com.ecommerce.presentation.viewmodel.CartViewModel
import com.google.gson.Gson


class ProductDetails : AppCompatActivity() {

    private var data: ProductsItem? = null
    private lateinit var categoryAdapter: ImgListAdapter
    private lateinit var binding: ActivityProductDetailsBinding
    private var sharedPreferences: SharedPreferences? = null
    private val viewModel: CartViewModel by viewModels {
        CartViewModel.CartViewModelFactory((application as Base).repositoryCart)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProductDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setData()
        setListener()
    }

    private fun setListener() {
        binding.addtocart.setOnClickListener {
            viewModel.addToCart(
                Cart(
                    System.currentTimeMillis().toString(),
                    data?.prName!!,
                    data?.brName!!,
                    data?.prPrice!!,
                    data?.prDescription!!,
                )
            )
            Toast.makeText(this, getString(com.ecommerce.R.string.prod_added), Toast.LENGTH_SHORT)
                .show()
        }
        binding.checkout.setOnClickListener {
            Toast.makeText(this, getString(com.ecommerce.R.string.under_imp), Toast.LENGTH_SHORT)
                .show()
        }
        binding.cart.setOnClickListener {
            val intent = Intent(this, CartListActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.getItemId()) {
            R.id.home -> {
                finish()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun setData() {
        sharedPreferences = this.getSharedPreferences("eCommercePrefs", Context.MODE_PRIVATE)
        this.setSupportActionBar(binding.toolbar)
        supportActionBar?.title = ""
        data = intent.getParcelableExtra<ProductsItem>("object")
        if (data == null) {
            val deepLink = Gson().fromJson(
                sharedPreferences!!.getString("deepLinkData", null),
                ProductsItem::class.java
            )
            data = deepLink
        }

        categoryAdapter = ImgListAdapter(this)
        binding.imagesList.apply {
            layoutManager =
                LinearLayoutManager(this@ProductDetails, LinearLayoutManager.HORIZONTAL, false)
            adapter = categoryAdapter
        }
        val helper: SnapHelper = LinearSnapHelper()
        helper.attachToRecyclerView(binding.imagesList)
        categoryAdapter.submitList(data?.prImage)
        binding.content.brand.text = data?.brName
        binding.content.price2.text = "AED. " + data?.prPrice
        binding.content.product.text = data?.prName
        binding.content.desc.text = data?.prDescription

        if (sharedPreferences!!.getBoolean("rtl", false)) {
            binding.content.price2.textDirection = View.LAYOUT_DIRECTION_RTL
            binding.content.brand.textDirection = View.LAYOUT_DIRECTION_RTL
            binding.content.product.textDirection = View.LAYOUT_DIRECTION_RTL
            binding.content.weightLabel.textDirection = View.LAYOUT_DIRECTION_RTL
            binding.content.desc.textDirection = View.LAYOUT_DIRECTION_RTL
        } else {
            binding.content.price2.textDirection = View.LAYOUT_DIRECTION_LTR
            binding.content.brand.textDirection = View.LAYOUT_DIRECTION_LTR
            binding.content.product.textDirection = View.LAYOUT_DIRECTION_LTR
            binding.content.weightLabel.textDirection = View.LAYOUT_DIRECTION_LTR
            binding.content.desc.textDirection = View.LAYOUT_DIRECTION_LTR
        }
    }
}