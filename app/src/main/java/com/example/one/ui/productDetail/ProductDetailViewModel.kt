package com.example.one.ui.productDetail

import androidx.lifecycle.*
import com.example.one.model.dataClass.Comment
import com.example.one.model.dataClass.Product
import com.example.one.model.repository.comment.CommentRepository
import com.example.one.util.EXTRA_KEY_PRODUCT_DATA
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductDetailViewModel @Inject constructor(
    private val saveStateHandle:SavedStateHandle,
    private val commentRepository: CommentRepository
):ViewModel() {

    private val _productDetailLiveData=MutableLiveData<Product>()
    val productDetailLiveData:LiveData<Product> get()=_productDetailLiveData

    private val _commentLiveData=MutableLiveData<List<Comment>>()
    val commentLiveData:LiveData<List<Comment>> get() = _commentLiveData

    init {
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
        }
    }
}