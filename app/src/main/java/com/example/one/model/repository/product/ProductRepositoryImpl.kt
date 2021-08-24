package com.example.one.model.repository.product

import com.example.one.model.dataClass.Product
import com.example.one.model.dataSource.product.ProductDataSource
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import timber.log.Timber
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val remoteDataSource: ProductDataSource,
    private val localDataSource: ProductDataSource,
    val coroutineExceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e(throwable)
    }
) : ProductRepository {

    override suspend fun getProducts(sort: Int): List<Product> =
        remoteDataSource.getProducts(sort)

    override suspend fun getFavoriteProducts(): List<Product> =
        localDataSource.getFavoriteProducts()

    override suspend fun addToFavorites(product: Product) =
        localDataSource.addToFavorites(product)

    override suspend fun deleteFromFavorites(product: Product) =
        localDataSource.deleteFromFavorites(product)

}