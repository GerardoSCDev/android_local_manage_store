package com.example.localmanagestore.CommonUtils.RoomDB.Entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class ProductEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val barcode: String,
    val name : String,
    val stock: Int,
    val detail: String,
    val photoPath: String,
    val insertAt: String,
    val updateAt: String,
)