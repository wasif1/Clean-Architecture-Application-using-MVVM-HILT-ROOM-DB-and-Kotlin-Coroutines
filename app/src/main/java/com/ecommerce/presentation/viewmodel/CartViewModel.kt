package com.ecommerce.presentation.viewmodel

import androidx.lifecycle.*
import com.ecommerce.model.Cart
import com.ecommerce.repository.CartRepository
import kotlinx.coroutines.launch

class CartViewModel (private  val cartRepo: CartRepository) : ViewModel() {

    val response: LiveData<List<Cart>> = cartRepo.allCart.asLiveData()

    fun addToCart(cart: Cart) {
        viewModelScope.launch {
            cartRepo.insert(cart)
        }
    }

    class CartViewModelFactory(private val repository: CartRepository) : ViewModelProvider.Factory {
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return CartViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}