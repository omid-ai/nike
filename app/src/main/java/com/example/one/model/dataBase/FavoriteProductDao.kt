package com.example.one.model.dataBase

import androidx.room.*
import com.example.one.model.dataClass.Product

@Dao
interface FavoriteProductDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addToFavorites(product: Product)

    @Delete()
    suspend fun deleteFromFavorites(product: Product)

    @Query("SELECT * FROM products")
    suspend fun getFavorites():List<Product>
}