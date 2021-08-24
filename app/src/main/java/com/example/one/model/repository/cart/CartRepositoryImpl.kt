package com.example.one.model.repository.cart

import com.example.one.model.dataClass.AddToCartResponse
import com.example.one.model.dataClass.CartItemCount
import com.example.one.model.dataClass.CartResponse
import com.example.one.model.dataClass.MessageResponse
import com.example.one.model.dataSource.cart.CartDataSource
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    private val remoteDataSource: CartDataSource
):CartRepository {

    override suspend fun addToCart(productId: Int): AddToCartResponse =
        remoteDataSource.addToCart(productId)

    override suspend fun removeFromCart(cartItemId: Int): MessageResponse =
        remoteDataSource.removeFromCart(cartItemId)

    override suspend fun getCartItemCount(): CartItemCount =
        remoteDataSource.getCartItemCount()

    override suspend fun changeCartItemCount(cartItemId: Int, count: Int): AddToCartResponse =
        remoteDataSource.changeCartItemCount(cartItemId,count)

    override suspend fun getCart(): CartResponse =
        remoteDataSource.getCart()
}