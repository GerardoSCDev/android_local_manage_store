package com.example.localmanagestore.CommonUtils.RoomDB.DAOs

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.localmanagestore.CommonUtils.RoomDB.Entities.ProductEntity

@Dao
interface ProductDAO {
    @Query("SELECT * FROM products")
    fun getAllNotes(): List<ProductEntity>

    @Insert
    fun insert(note: ProductEntity)

    @Update
    fun update(note: ProductEntity)

    @Delete
    fun delete(note: ProductEntity)
}