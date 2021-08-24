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

    override suspend fun removeFromCart(cartItemId: Int): MessageResponse =
        apiService.removeFromCart(JsonObject().apply {
            addProperty("cart_item_id",cartItemId)
        })

    override suspend fun getCartItemCount(): CartItemCount =
        apiService.getCartItemCount()

    override suspend fun changeCartItemCount(cartItemId: Int, count: Int): AddToCartResponse =
        apiService.changeCartItemCount(JsonObject().apply {
            addProperty("cart_item_id",cartItemId)
            addProperty("count",count)
        })

    override suspend fun getCart(): CartResponse =
        apiService.fetchCart()
}