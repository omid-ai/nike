package com.example.one.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one.model.dataClass.CartResponse
import com.example.one.model.dataClass.PurchaseDetail
import com.example.one.model.repository.cart.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val cartRepository: CartRepository
):ViewModel() {

    private val _cartItemLiveData=MutableLiveData<CartResponse>()
    val cartItemLiveData:LiveData<CartResponse> get() = _cartItemLiveData

    private val _purchaseDetailLiveData=MutableLiveData<PurchaseDetail>()
    val purchaseDetailLiveData:LiveData<PurchaseDetail> get()=_purchaseDetailLiveData

    fun getCart(){
        viewModelScope.launch {

        }
    }
}