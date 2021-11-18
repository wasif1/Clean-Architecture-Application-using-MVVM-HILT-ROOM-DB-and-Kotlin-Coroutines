package com.ecommerce.presentation

import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.Base
import com.ecommerce.databinding.ActivityCartBinding
import com.ecommerce.presentation.adapter.CartListAdapter
import com.ecommerce.presentation.viewmodel.CartViewModel

class CartListActivity : AppCompatActivity() {

    /**
     * VARIABLE DECLARATIONS
     */
    private lateinit var binding: ActivityCartBinding
    private lateinit var adapter: CartListAdapter
    private val viewModel: CartViewModel by viewModels{
        CartViewModel.CartViewModelFactory((application as Base).repositoryCart)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
        init()
        setData()
    }

    private fun setData() {
        viewModel.response.observe(this, Observer {
            if (it!=null && it.size > 0) binding.noRecord.visibility = View.GONE
            else binding.noRecord.visibility = View.VISIBLE
            adapter.submitList(it)
        })
    }

    private fun init() {
        adapter = CartListAdapter()
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
    }

    private fun setListener() {
        binding.back.setOnClickListener {
            finish()
        }
    }
}