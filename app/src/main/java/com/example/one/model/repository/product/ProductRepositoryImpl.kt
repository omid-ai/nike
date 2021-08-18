package com.example.one.model.repository.product

import com.example.one.model.dataClass.Product
import com.example.one.model.dataSource.product.ProductDataSource
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource:ProductDataSource
):ProductRepository {
    override suspend fun getProducts(): List<Product> {
        return remoteDataSource.getProducts()
    }

    override suspend fun getFavoriteProducts(): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun addToFavorites(product: Product) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFromFavorites(product: Product) {
        TODO("Not yet implemented")
    }
}