package com.example.localmanagestore.CommonUtils.RoomDB.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.localmanagestore.CommonUtils.RoomDB.DAOs.ProductDAO
import com.example.localmanagestore.CommonUtils.RoomDB.Entities.ProductEntity

@Database(entities = [ProductEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDAO

    companion object {
        @Volatile private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "localManageStoreDB"
                ).allowMainThreadQueries().build()
                INSTANCE = instance
                instance
            }
        }
    }
}