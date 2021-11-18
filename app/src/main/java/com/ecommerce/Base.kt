package com.ecommerce

import android.app.Application
import androidx.multidex.MultiDex
import com.ecommerce.dao.EcommerceRoomDatabase
import com.ecommerce.repository.AddressRepository
import com.ecommerce.repository.CartRepository
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class Base : Application() {
    // Using by lazy so the database and the repository are only created when they're needed
    // rather than when the application starts
    val database by lazy { EcommerceRoomDatabase.getDatabase(this) }
    val repository by lazy { AddressRepository(database.addressDao()) }
    val repositoryCart by lazy { CartRepository(database.cartDao()) }

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
    }
}