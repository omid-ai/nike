package com.example.one.model.dataSource.order

import com.example.one.model.apiService.NikeApiService
import com.example.one.model.dataClass.Checkout
import com.example.one.model.dataClass.OrderHistoryItem
import com.example.one.model.dataClass.SubmitOrderResult
import com.google.gson.JsonObject
import javax.inject.Inject

class OrderRemoteDataSource @Inject constructor(
    private val apiService: NikeApiService
):OrderDataSource {
    override suspend fun submitOrder(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        postalCode: String,
        address: String,
        paymentMethod: String
    ):SubmitOrderResult {
        return apiService.submitOrder(JsonObject().apply {
            addProperty("first_name", firstName)
            addProperty("last_name", lastName)
            addProperty("postal_code", postalCode)
            addProperty("mobile", phoneNumber)
            addProperty("address", address)
            addProperty("payment_method", paymentMethod)
        })
    }

    override suspend fun checkout(orderId: Int): Checkout =
        apiService.checkout(orderId)

    override suspend fun getOrderHistory(): List<OrderHistoryItem> =
        apiService.fetchOrderHistory()
}