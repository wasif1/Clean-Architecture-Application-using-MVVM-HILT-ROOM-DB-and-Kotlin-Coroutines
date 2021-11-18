package com.ecommerce.presentation.fragment

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.ecommerce.databinding.ProfileBinding
import com.ecommerce.presentation.AddAddressActivity
import com.google.gson.Gson


class Profile : Fragment() {

    private var sharedPreferences: SharedPreferences? = null
    private lateinit var binding: ProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = ProfileBinding.inflate(layoutInflater)
        sharedPreferences = context?.getSharedPreferences("eCommercePrefs", Context.MODE_PRIVATE)
        setListener()
        return binding.root
    }

    private fun setListener() {
        binding.locationToggle.isChecked = ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.notifyToggle.isChecked = ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            ) == PackageManager.PERMISSION_GRANTED
        }

        binding.rtlToggle.isChecked = sharedPreferences?.getBoolean("rtl", false) == true

        binding.rtlToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            val editor = sharedPreferences!!.edit()
            editor.putBoolean("rtl", isChecked)
            editor.apply()
        }

        binding.notifyToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val uri: Uri = Uri.fromParts("package", context?.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
        binding.locationToggle.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                val uri: Uri = Uri.fromParts("package", context?.packageName, null)
                intent.data = uri
                startActivity(intent)
            }
        }
        binding.address.setOnClickListener {
            val intent = Intent(context, AddAddressActivity::class.java)
            startActivity(intent)
        }
    }
}