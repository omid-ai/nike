package com.example.one.ui.checkout

import androidx.lifecycle.*
import com.example.one.model.dataClass.Checkout
import com.example.one.model.repository.order.OrderRepository
import com.example.one.util.EXTRA_KEY_ID_CHECKOUT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CheckOutViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _checkoutLiveData = MutableLiveData<Checkout>()
    val checkoutLiveData: LiveData<Checkout> get() = _checkoutLiveData

    private val _orderIdLiveData = MutableLiveData<Int>()
    val orderIdLiveData: LiveData<Int> get() = _orderIdLiveData

    val coroutineExceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e("error in receiving checkout detail-> $throwable")
    }

    init {
        _orderIdLiveData.value = getOrderDetail(savedStateHandle)
    }

    private fun getOrderDetail(savedStateHandle: SavedStateHandle): Int {
        return savedStateHandle.get<Int>(EXTRA_KEY_ID_CHECKOUT)!!
    }

    fun provideCheckOutDetail(orderId: Int) {
        viewModelScope.launch(coroutineExceptionHandler) {
            val checkoutDetail = async {
                orderRepository.checkout(orderId)
            }
            _checkoutLiveData.postValue(checkoutDetail.await())
            Timber.i("checkout data in view model-> $checkoutDetail")
        }
    }
}