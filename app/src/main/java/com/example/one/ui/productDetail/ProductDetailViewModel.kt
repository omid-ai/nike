package com.example.one.ui.productDetail

import android.widget.Toast
import androidx.lifecycle.*
import com.example.one.common.base.NikeViewModel
import com.example.one.common.exceptionHandling.NikeExceptionMapper
import com.example.one.common.exceptionHandling.Result
import com.example.one.model.dataClass.Comment
import com.example.one.model.dataClass.Product
import com.example.one.model.repository.cart.CartRepository
import com.example.one.model.repository.comment.CommentRepository
import com.example.one.util.EXTRA_KEY_PRODUCT_DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import org.greenrobot.eventbus.EventBus
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val saveStateHandle:SavedStateHandle,
    private val commentRepository: CommentRepository,
    private val cartRepository: CartRepository
):NikeViewModel() {

    private val _productDetailLiveData=MutableLiveData<Product>()
    val productDetailLiveData:LiveData<Product> get()=_productDetailLiveData

    private val _commentLiveData=MutableLiveData<List<Comment>>()
    val commentLiveData:LiveData<List<Comment>> get() = _commentLiveData

    private val _addToCartResultLiveData=MutableLiveData<Result<Boolean>>()
    val addToCartResultLiveData:LiveData<Result<Boolean>> get()=_addToCartResultLiveData


//    val exceptionHandler=CoroutineExceptionHandler{
//        coroutineContext: CoroutineContext, throwable: Throwable ->
//        EventBus.getDefault().post(NikeExceptionMapper.mapper(throwable))
//        Timber.e("auth error->$throwable")
//    }


    init {
        progressBarLiveData.value=true
        getProduct()
        getComment()
    }

    private fun getProduct():Product{
        val product=saveStateHandle.get<Product>(EXTRA_KEY_PRODUCT_DATA)
        _productDetailLiveData.value=product!!
        return product
    }

    private fun getComment(){
        viewModelScope.launch {
            val comments=commentRepository.getComments(getProduct().id)
            _commentLiveData.postValue(comments)
            progressBarLiveData.postValue(false)
        }
    }

//    fun addToCart(){
//            viewModelScope.launch(exceptionHandler) {
//                cartRepository.addToCart(getProduct().id)
//            }
//        }

    fun addToCart(){
        viewModelScope.launch {
            try {
                cartRepository.addToCart(getProduct().id)
//                EventBus.getDefault().post(NikeExceptionMapper.onSuccess())
                _addToCartResultLiveData.postValue(Result.Success(true,""))
            }catch (t:Throwable){
                EventBus.getDefault().post(NikeExceptionMapper.mapper(t))
                _addToCartResultLiveData.postValue(Result.Error(false,""))
            }
        }
    }
}