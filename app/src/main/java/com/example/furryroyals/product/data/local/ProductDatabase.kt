package com.example.furryroyals.product.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.furryroyals.product.data.utils.Converters

@Database(
    entities = [ProductEntity::class, CategoryEntity::class],
    version = 1
)
@TypeConverters(Converters::class)
abstract class ProductDatabase: RoomDatabase() {
    abstract val productDao: ProductDao
    abstract val categoryDao: CategoryDao
}