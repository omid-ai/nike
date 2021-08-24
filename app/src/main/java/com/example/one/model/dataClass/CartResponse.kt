package com.example.one.model.dataClass

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class CartResponse(
    val cart_items: List<CartItem>,
    val payable_price: Int,
    val shipping_cost: Int,
    val total_price: Int
)

@Parcelize
data class PurchaseDetail(var payablePrice:Int, val shippingCost:Int, var totalPrice:Int) :
    Parcelable
