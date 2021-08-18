package com.example.one.model.dataSource.product

import com.example.one.model.dataClass.Product

interface ProductDataSource {

    suspend fun getProducts():List<Product>

    suspend fun getFavoriteProducts():List<Product>

    suspend fun addToFavorites(product: Product)

    suspend fun deleteFromFavorites(product: Product)
}