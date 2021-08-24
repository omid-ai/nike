package com.example.one.ui.orderHistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one.model.dataClass.OrderHistoryItem
import com.example.one.model.repository.order.OrderRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OrderHistoryViewModel @Inject constructor(
    private val orderRepository: OrderRepository
):ViewModel() {

    private val _orderHistoryLiveData=MutableLiveData<List<OrderHistoryItem>>()
    val orderHistoryLiveData:LiveData<List<OrderHistoryItem>> get()=_orderHistoryLiveData

    init {
        getOrderHistory()
    }

    private fun getOrderHistory(){
        viewModelScope.launch {
            val ordersHistoryList=orderRepository.getOrderHistory()
            _orderHistoryLiveData.postValue(ordersHistoryList)
        }
    }
}