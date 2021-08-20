package com.example.one.model.repository.cart

import com.example.one.model.dataClass.AddToCartResponse
import com.example.one.model.dataClass.CartItemCount
import com.example.one.model.dataClass.CartResponse
import com.example.one.model.dataClass.MessageResponse

interface CartRepository {

    suspend fun addToCart(productId:Int):AddToCartResponse

    suspend fun removeFromCart(cartItemId:Int):MessageResponse

    suspend fun getCartItemCount():CartItemCount

    suspend fun changeCartItemCount(cartItemId: Int,count:Int):AddToCartResponse

    suspend fun getCart():CartResponse
}