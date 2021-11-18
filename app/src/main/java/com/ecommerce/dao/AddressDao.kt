package com.ecommerce.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ecommerce.model.Address
import kotlinx.coroutines.flow.Flow

@Dao
interface AddressDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addAddress(address: Address)

    @Query("Select * FROM Addresses")
    fun getAddress(): Flow<List<Address>>

    @Query("DELETE FROM Addresses")
    fun deleteRecord()
}