package com.ecommerce.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "Addresses")
data class Address(
    @PrimaryKey @ColumnInfo(name = "Address") val name: String,
    @ColumnInfo(name = "latitude") val lat: String,
    @ColumnInfo(name = "longitude") val lon: String,
)
