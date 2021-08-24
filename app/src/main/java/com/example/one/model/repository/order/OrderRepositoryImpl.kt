package com.example.one.model.repository.order

import com.example.one.model.dataClass.Checkout
import com.example.one.model.dataClass.OrderHistoryItem
import com.example.one.model.dataClass.SubmitOrderResult
import com.example.one.model.dataSource.order.OrderDataSource
import javax.inject.Inject

class OrderRepositoryImpl @Inject constructor(
    private val remoteDataSource: OrderDataSource
):OrderRepository {
    override suspend fun submitOrder(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        postalCode: String,
        address: String,
        paymentMethod: String
    ): SubmitOrderResult =
        remoteDataSource.submitOrder(firstName,lastName,phoneNumber,postalCode,address,paymentMethod)

    override suspend fun checkout(orderId: Int): Checkout =
        remoteDataSource.checkout(orderId)

    override suspend fun getOrderHistory(): List<OrderHistoryItem> =
        remoteDataSource.getOrderHistory()
}