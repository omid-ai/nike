package com.example.one.ui.list

import androidx.lifecycle.*
import com.example.one.R
import com.example.one.common.base.NikeViewModel
import com.example.one.model.dataClass.Product
import com.example.one.model.repository.product.ProductRepository
import com.example.one.util.EXTRA_KEY_PRODUCT_SORT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductListViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val productRepository: ProductRepository
): NikeViewModel(){

    val sortNames= arrayOf(R.string.newest,R.string.popular,R.string.highPrice,R.string.lowPrice)

    private val _productListLiveData=MutableLiveData<List<Product>>()
    val productListLiveData:LiveData<List<Product>> get() = _productListLiveData

    private val _selectedSortTitleLiveData=MutableLiveData<Int>()
    val selectedSortTitleLiveData:LiveData<Int> get() = _selectedSortTitleLiveData


    private val _sortState=MutableLiveData<Int>()
    val sortState:LiveData<Int> get() = _sortState

    init {
        progressBarLiveData.value=true
        _selectedSortTitleLiveData.value=sortNames[getSort()]
        _sortState.value=getSort()
    }

    private fun getSort():Int{
        return savedStateHandle.get<Int>(EXTRA_KEY_PRODUCT_SORT)!!
    }

    fun onSortChangedByUser(sort:Int){
        _sortState.postValue(sort)
        _selectedSortTitleLiveData.postValue(sortNames[sort])
        viewModelScope.launch {
            val products=productRepository.getProducts(sort)
            _productListLiveData.postValue(products)
            progressBarLiveData.postValue(false)
        }
    }
}