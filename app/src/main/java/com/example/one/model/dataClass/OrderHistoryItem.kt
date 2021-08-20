package com.example.one.model.dataClass

import com.example.one.model.dataClass.OrderItem

data class OrderHistoryItem(
    val id: Int,
    val payable: Int,
    val order_items: List<OrderItem>,

    )