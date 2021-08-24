package com.example.one.model.repository.order

import com.example.one.model.dataClass.Checkout
import com.example.one.model.dataClass.OrderHistoryItem
import com.example.one.model.dataClass.SubmitOrderResult

interface OrderRepository {

    suspend fun submitOrder(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        postalCode: String,
        address: String,
        paymentMethod: String
    ):SubmitOrderResult

    suspend fun checkout(orderId: Int): Checkout

    suspend fun getOrderHistory():List<OrderHistoryItem>
}