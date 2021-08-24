package com.example.one.model.dataSource.product

import com.example.one.model.dataBase.FavoriteProductDB
import com.example.one.model.dataBase.FavoriteProductDao
import com.example.one.model.dataClass.Product
import javax.inject.Inject

class ProductLocalDataSource @Inject constructor(
    private val favoriteProductDao: FavoriteProductDao
):ProductDataSource {

    override suspend fun getProducts(sort: Int): List<Product> {
        TODO("Not yet implemented")
    }

    override suspend fun getFavoriteProducts(): List<Product> =
        favoriteProductDao.getFavorites()

    override suspend fun addToFavorites(product: Product) =
        favoriteProductDao.addToFavorites(product)

    override suspend fun deleteFromFavorites(product: Product) =
        favoriteProductDao.deleteFromFavorites(product)
}