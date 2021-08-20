package com.example.one.model.dataSource.product

import com.example.one.model.apiService.NikeApiService
import com.example.one.model.dataClass.Product
import javax.inject.Inject

class ProductRemoteDataSource @Inject constructor(
    private val apiService: NikeApiService
):ProductDataSource {
    override suspend fun getProducts(sort:Int): List<Product> {
        return apiService.fetchProduct(sort.toString())
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