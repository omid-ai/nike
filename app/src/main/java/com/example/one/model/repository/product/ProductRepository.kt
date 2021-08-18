package com.example.one.model.repository.product

import com.example.one.model.dataClass.Product

interface ProductRepository {

    suspend fun getProducts():List<Product>

    suspend fun getFavoriteProducts():List<Product>

    suspend fun addToFavorites(product: Product)

    suspend fun deleteFromFavorites(product: Product)
}