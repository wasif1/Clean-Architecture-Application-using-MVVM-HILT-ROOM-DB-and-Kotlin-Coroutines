package com.ecommerce.dao

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.ecommerce.model.Address
import com.ecommerce.model.Cart

@Database(entities = [Cart::class, Address::class], version = 1, exportSchema = false)
abstract class EcommerceRoomDatabase : RoomDatabase() {

    abstract fun addressDao(): AddressDao
    abstract fun cartDao(): CartDao

    companion object {
        @Volatile
        private var INSTANCE: EcommerceRoomDatabase? = null

        fun getDatabase(context: Context): EcommerceRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    EcommerceRoomDatabase::class.java,
                    "eCommerce"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}