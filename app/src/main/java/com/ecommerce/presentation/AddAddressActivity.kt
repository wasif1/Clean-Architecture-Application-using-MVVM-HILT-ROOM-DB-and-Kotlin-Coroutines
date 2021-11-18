package com.ecommerce.presentation

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.ecommerce.Base
import com.ecommerce.databinding.ActivityAddAddressBinding
import com.ecommerce.model.Address
import com.ecommerce.presentation.adapter.AddressListAdapter
import com.ecommerce.presentation.viewmodel.AddressViewModel

class AddAddressActivity : AppCompatActivity() {

    /**
     * VARIABLE DECLARATIONS
     */
    private lateinit var binding: ActivityAddAddressBinding
    private lateinit var adapter: AddressListAdapter
    private val viewModel: AddressViewModel by viewModels{
        AddressViewModel.AddressViewModelFactory((application as Base).repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddAddressBinding.inflate(layoutInflater)
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
        adapter = AddressListAdapter()
        binding.list.adapter = adapter
        binding.list.layoutManager = LinearLayoutManager(this)
    }

    private fun setListener() {
        binding.back.setOnClickListener {
            finish()
        }
        binding.add.setOnClickListener {
            val intent = Intent(this, AddressActivity::class.java)
            startActivityIfNeeded(intent, 101)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 101) {
            viewModel.addAddress(
                Address(
                    data?.getStringExtra("name").toString(),
                    data?.getStringExtra("lat").toString(),
                    data?.getStringExtra("long").toString()
                )
            )
        }
    }
}