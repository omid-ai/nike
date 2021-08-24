package com.example.one.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one.R
import com.example.one.common.base.NikeViewModel
import com.example.one.common.exceptionHandling.NikeExceptionMapper
import com.example.one.model.TokenContainer
import com.example.one.model.dataClass.CartItem
import com.example.one.model.dataClass.EmptyState
import com.example.one.model.dataClass.PurchaseDetail
import com.example.one.model.repository.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
) : NikeViewModel() {

    private val _cartItemLiveData = MutableLiveData<List<CartItem>>()
    val cartItemLiveData: LiveData<List<CartItem>> get() = _cartItemLiveData

    private val _purchaseDetailsLiveData = MutableLiveData<PurchaseDetail>()
    val purchaseDetailsLiveData: LiveData<PurchaseDetail> get() = _purchaseDetailsLiveData

    private val _emptyStateLiveData = MutableLiveData<EmptyState>()
    val emptyStateLiveData: LiveData<EmptyState> get() = _emptyStateLiveData

    val exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
        EventBus.getDefault().post(NikeExceptionMapper.mapper(throwable))
        Timber.e("cart item count error->$throwable")
    }

    init {
        progressBarLiveData.value=true
    }

    private fun getCart() {
        if (!TokenContainer.token.isNullOrEmpty()) {
            viewModelScope.launch {
                cartRepository.getCart().let {
                    if (it.cart_items.isNotEmpty()) {
                        progressBarLiveData.postValue(false)
                        _emptyStateLiveData.postValue(EmptyState(false))
                        _cartItemLiveData.postValue(it.cart_items)
                        _purchaseDetailsLiveData.postValue(PurchaseDetail(it.payable_price, it.shipping_cost, it.total_price))
                    }
                        else{
                            _emptyStateLiveData.postValue(EmptyState(true,R.string.cartEmptyState))
                        }
                }
            }
        }else
            _emptyStateLiveData.value=EmptyState(
                true,
                R.string.cartEmptyStateLoggingRequired,
                true
            )
    }


//    private fun processOfEmptyStateStatus(
//    ) {
//        _cartItemLiveData.value?.let { data ->
//            if (data.isEmpty())
//                _emptyStateLiveData.postValue(EmptyState(
//                    true,
//                    R.string.cartEmptyState
//                ))
//            else
//                _emptyStateLiveData.postValue(EmptyState(false))
//        }
//    }

    fun increaseCartItemCount(cartItem: CartItem) {
        viewModelScope.launch(exceptionHandler) {
            val onIncrease = async {
                cartRepository.changeCartItemCount(cartItem.cart_item_id, ++cartItem.count)
            }
            calculateAndPublishPurchaseDetail(onIncrease)
        }
    }

    fun decreaseCartItemCount(cartItem: CartItem) {
        viewModelScope.launch(exceptionHandler) {
            val onDecrease = async {
                cartRepository.changeCartItemCount(cartItem.cart_item_id, --cartItem.count)
            }
            calculateAndPublishPurchaseDetail(onDecrease)
        }
    }

    fun removeItemFromCart(cartItem: CartItem) {
        viewModelScope.launch {
            val onRemove = async {
                cartRepository.removeFromCart(cartItem.cart_item_id)
            }
            calculateAndPublishPurchaseDetail(onRemove)
//            processOfEmptyStateStatus()
        }
    }

    fun refreshCart() {
        getCart()
//        processOfEmptyStateStatus()
    }

    private fun calculateAndPublishPurchaseDetail(T: Any) {
        _cartItemLiveData.value?.let {
            _purchaseDetailsLiveData.value?.let { purchaseDetail ->
                var payablePrice = 0
                var totalPrice = 0
                it.forEach {
                    payablePrice += it.product.price * it.count
                    totalPrice += (it.product.price - it.product.discount) * it.count
                }
                purchaseDetail.payablePrice = payablePrice
                purchaseDetail.totalPrice = totalPrice
                _purchaseDetailsLiveData.postValue(purchaseDetail)
            }
        }
    }
}