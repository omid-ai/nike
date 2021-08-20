package com.example.one.model.dataClass

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)

data class PurchaseDetail(val payablePrice:Int, val shippingCost:Int, val totalPrice:Int)
