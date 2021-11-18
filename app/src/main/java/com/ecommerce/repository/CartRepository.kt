package com.ecommerce.repository

import androidx.annotation.WorkerThread
import com.ecommerce.dao.CartDao
import com.ecommerce.model.Cart
import kotlinx.coroutines.flow.Flow

class CartRepository(private val cartDao: CartDao) {

    val allCart: Flow<List<Cart>> = cartDao.getCart()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(cart: Cart) {
        cartDao.addToCart(cart)
    }
}