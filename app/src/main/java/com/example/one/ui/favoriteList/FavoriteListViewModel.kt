package com.example.one.ui.favoriteList

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one.model.dataClass.Product
import com.example.one.model.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class FavoriteListViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e("e-> $throwable")
    }

    private val _favoriteProductsLiveData = MutableLiveData<List<Product>>()
    val favoriteProductsLiveData: LiveData<List<Product>> get() = _favoriteProductsLiveData

    init {
        getFavoriteProducts()
    }

    private fun getFavoriteProducts() {
        viewModelScope.launch(exceptionHandler) {
            _favoriteProductsLiveData.postValue(productRepository.getFavoriteProducts())
        }
    }

    fun deleteProductFromFavoriteList(product: Product){
        viewModelScope.launch(exceptionHandler) {
            productRepository.deleteFromFavorites(product)
        }
//        getFavoriteProducts()
    }
}