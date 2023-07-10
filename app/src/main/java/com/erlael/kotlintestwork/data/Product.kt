package com.erlael.kotlintestwork.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Product(
    @PrimaryKey(autoGenerate = true)
    val id : Long = 0,

    val name : String,
    val count : Int,
    val price : Int,
)