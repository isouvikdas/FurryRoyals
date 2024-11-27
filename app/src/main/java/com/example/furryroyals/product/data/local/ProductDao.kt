package com.example.furryroyals.product.data.local

import android.database.Cursor
import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert

@Dao
interface ProductDao {

    @Upsert
    suspend fun upsertAll(products: List<ProductEntity>)

    @Insert
    suspend fun insertAll(products: List<ProductEntity>)

    @Query("SELECT * FROM products")
    fun pagingSource(): PagingSource<Int, ProductEntity>

    @Query("DELETE FROM products")
    suspend fun clearAll()

    @Query("SELECT * FROM products WHERE localId = :localId")
    suspend fun getProductByLocalId(localId: Long): ProductEntity?

    @Query("SELECT * FROM products WHERE localId > :localCursor ORDER BY localId ASC")
    suspend fun getProductsAfterCursor(localCursor: Long): List<ProductEntity>


}