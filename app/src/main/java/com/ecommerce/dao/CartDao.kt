package com.ecommerce.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecommerce.model.Cart
import kotlinx.coroutines.flow.Flow

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addToCart(cart: Cart)

    @Query("SELECT * FROM CartData")
    fun getCart(): Flow<List<Cart>>

    @Query("DELETE FROM CartData")
    fun clearCart()
}