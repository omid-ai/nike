package com.example.one.ui.shipping

import android.content.Context
import android.content.Intent
import androidx.lifecycle.*
import com.example.one.model.dataClass.PurchaseDetail
import com.example.one.model.repository.order.OrderRepository
import com.example.one.ui.checkout.CheckOutActivity
import com.example.one.util.EXTRA_KEY_CART_ITEMS_PURCHASE_DETAIL
import com.example.one.util.EXTRA_KEY_ID_CHECKOUT
import com.example.one.util.UtilFunctions.openUrlInCustomTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ShippingViewModel @Inject constructor(
    private val orderRepository: OrderRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _purchaseDetailLiveData = MutableLiveData<PurchaseDetail>()
    val purchaseDetailLiveData: LiveData<PurchaseDetail> get() = _purchaseDetailLiveData

    private val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e("error in shipping view model->$throwable")
    }

    init {
        _purchaseDetailLiveData.value = fetchPurchaseDetail(savedStateHandle)
    }

    private fun fetchPurchaseDetail(savedStateHandle: SavedStateHandle): PurchaseDetail? {
        return savedStateHandle.get<PurchaseDetail>(EXTRA_KEY_CART_ITEMS_PURCHASE_DETAIL)
    }

    fun submitOrders(
        firstName: String,
        lastName: String,
        phoneNumber: String,
        postalCode: String,
        address: String,
        paymentMethod: String,
        context: Context
    ) {
        viewModelScope.launch(exceptionHandler) {
            orderRepository.submitOrder(
                firstName,
                lastName,
                phoneNumber,
                postalCode,
                address,
                paymentMethod
            ).let {
                if (it.bank_gateway_url.isNotEmpty()){
                    openUrlInCustomTab(
                        context,it.bank_gateway_url
                    )
                }else{
                    context.startActivity(Intent(context,CheckOutActivity::class.java).apply {
                        putExtra(EXTRA_KEY_ID_CHECKOUT,it.order_id)
                    })
                }
            }
        }
    }
}