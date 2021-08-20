package com.example.one.model.dataSource.cart

import com.example.one.model.apiService.NikeApiService
import com.example.one.model.dataClass.AddToCartResponse
import com.example.one.model.dataClass.CartItemCount
import com.example.one.model.dataClass.CartResponse
import com.example.one.model.dataClass.MessageResponse
import com.google.gson.JsonObject
import javax.inject.Inject

class CartRemoteDataSource @Inject constructor(
    private val apiService: NikeApiService
):CartDataSource {

    override suspend fun addToCart(productId: Int): AddToCartResponse =
        apiService.addToCart(JsonObject().apply {
            addProperty("product_id",productId)
        })

    override suspend fun removeFromCart(cartItemId: Int): MessageResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getCartItemCount(): CartItemCount {
        TODO("Not yet implemented")
    }

    override suspend fun changeCartItemCount(cartItemId: Int, count: Int): AddToCartResponse {
        TODO("Not yet implemented")
    }

    override suspend fun getCart(): CartResponse =
        apiService.fetchCart()
}