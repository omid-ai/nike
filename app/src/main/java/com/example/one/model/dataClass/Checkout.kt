package com.example.one.model.dataClass

data class Checkout(
    val payable_price: Int,
    val payment_status: String,
    val purchase_success: Boolean
)