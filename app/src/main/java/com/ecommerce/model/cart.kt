package com.ecommerce.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "CartData")
data class Cart(
    @PrimaryKey @ColumnInfo(name = "id") val id: String,
    @ColumnInfo(name = "product") val name: String,
    @ColumnInfo(name = "brand") val brand: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "desc") val desc: String,
)
