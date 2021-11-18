package com.ecommerce.presentation

import android.Manifest
import android.Manifest.permission
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.location.*
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.ecommerce.R
import com.ecommerce.databinding.ActivityAddressBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMapLongClickListener
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import java.io.IOException
import java.util.*


class AddressActivity : AppCompatActivity(), OnMapReadyCallback,
    OnMapLongClickListener, LocationListener {

    /**
     * VARIABLE DECLARATIONS
     */
    private lateinit var binding: ActivityAddressBinding
    private val provider = LocationManager.NETWORK_PROVIDER
    private var locationManager: LocationManager? = null
    private var permissionsToRequest: ArrayList<String>? = null
    private val permissionsRejected = ArrayList<String>()
    private val permissions = ArrayList<String>()
    private val ALL_PERMISSIONS_RESULT = 101
    private var longitude = 0.0
    private var latitude = 0.0
    private var mMap: GoogleMap? = null
    private var centerLat = 0.0
    private var centerLong = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddressBinding.inflate(layoutInflater)
        setContentView(binding.root)
        init()
        setListener()
    }

    private fun setListener() {
        binding.myLocation.setOnClickListener { moveMap() }
        binding.add.setOnClickListener {
            setResult(
                RESULT_OK, Intent()
                    .putExtra("name", centerLat.let { it1 ->
                        centerLong.let { it2 ->
                            getAddress(
                                it1,
                                it2
                            )
                        }
                    })
                    .putExtra("lat", ""+centerLat)
                    .putExtra("long", ""+centerLong)
            )
            finish()
        }
    }

    fun getAddress(lat: Double, lng: Double): String? {
        val geocoder = Geocoder(this, Locale.getDefault())
        try {
            val addresses: List<Address> = geocoder.getFromLocation(lat, lng, 1)
            val obj: Address = addresses[0]
            var add: String = obj.getAddressLine(0)
            Log.v("IGA", "Address$add")
            return add

        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            Toast.makeText(this, e.message, Toast.LENGTH_SHORT).show()
            return null
        }
    }

    private fun init() {
        locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(this)
        askPermission()
    }

    private fun askPermission() {
        permissions.add(permission.ACCESS_FINE_LOCATION)
        permissions.add(permission.ACCESS_COARSE_LOCATION)
        permissionsToRequest = findUnAskedPermissions(permissions)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (permissionsToRequest?.size!! > 0)
                permissionsToRequest?.toTypedArray<String>()?.let {
                    requestPermissions(
                        it,
                        ALL_PERMISSIONS_RESULT
                    )
                }
        }
    }

    private fun findUnAskedPermissions(wanted: ArrayList<String>): ArrayList<String>? {
        val result = ArrayList<String>()
        for (perm in wanted) {
            if (!hasPermission(perm)) {
                result.add(perm)
            }
        }
        return result
    }

    private fun hasPermission(permission: String): Boolean {
        if (canMakeSmores()) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                return checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED
            }
        }
        return true
    }

    private fun canMakeSmores(): Boolean {
        return Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1
    }

    override fun onResume() {
        super.onResume()
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        locationManager!!.requestLocationUpdates(provider, 400, 1f, this)
    }

    override fun onPause() {
        super.onPause()
        locationManager!!.removeUpdates(this)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            ALL_PERMISSIONS_RESULT -> {
                for (perms in permissionsToRequest!!) {
                    if (!hasPermission(perms)) {
                        permissionsRejected.add(perms)
                    }
                }
                if (permissionsRejected.size > 0) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (shouldShowRequestPermissionRationale(permissionsRejected[0])) {
                            showMessageOKCancel(
                                "These permissions are mandatory for the application. Please allow access."
                            ) { dialog, which ->
                                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                                    requestPermissions(
                                        permissionsRejected.toTypedArray(),
                                        ALL_PERMISSIONS_RESULT
                                    )
                                }
                            }
                            return
                        }
                    }
                }
            }
        }
    }

    private fun showMessageOKCancel(message: String, okListener: DialogInterface.OnClickListener) {
        AlertDialog.Builder(this@AddressActivity)
            .setMessage(message)
            .setPositiveButton("OK", okListener)
            .setNegativeButton("Cancel", null)
            .create()
            .show()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap?.setOnMapLongClickListener(this)
        mMap?.setOnCameraMoveListener {
            centerLat = mMap?.cameraPosition!!.target.latitude
            centerLong = mMap?.cameraPosition!!.target.longitude
        }
        moveMap()
    }

    private fun moveMap() {
        if (mMap != null) {
            mMap!!.clear()
            val latLng = LatLng(latitude, longitude)
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(latLng))
            mMap!!.animateCamera(CameraUpdateFactory.zoomTo(15f))
        }
    }

    override fun onMapLongClick(p0: LatLng) {

    }

    override fun onLocationChanged(location: Location) {
        latitude = location.latitude
        longitude = location.longitude
        moveMap()
        locationManager!!.removeUpdates(this)
    }
}