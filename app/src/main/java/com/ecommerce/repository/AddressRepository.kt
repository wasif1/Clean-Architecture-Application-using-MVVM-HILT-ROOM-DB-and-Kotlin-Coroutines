package com.ecommerce.repository

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData
import com.ecommerce.dao.AddressDao
import com.ecommerce.model.Address
import kotlinx.coroutines.flow.Flow

class AddressRepository(private val addressDao: AddressDao) {

//    var addressDao : AddressDao? = null
//
//    constructor(dao: AddressDao) : this() {
//        this.addressDao = dao
//    }

    val allAddress: Flow<List<Address>> = addressDao.getAddress()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(address: Address) {
        addressDao.addAddress(address)
    }
}