package com.ecommerce.presentation.viewmodel

import androidx.lifecycle.*
import com.ecommerce.model.Address
import com.ecommerce.repository.AddressRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class AddressViewModel (private  val addressRepo: AddressRepository) : ViewModel() {

//    var addressRepo : AddressRepository? = null
//
//    constructor(address: AddressRepository) : this() {
//        this.addressRepo = address
//    }

    val response: LiveData<List<Address>> = addressRepo.allAddress.asLiveData()

    fun addAddress(address: Address) {
        viewModelScope.launch {
            addressRepo.insert(address)
        }
    }

    class AddressViewModelFactory(private val repository: AddressRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AddressViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AddressViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}