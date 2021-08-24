package com.example.one.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.one.common.base.NikeViewModel
import com.example.one.model.dataClass.Banner
import com.example.one.model.dataClass.Product
import com.example.one.model.repository.banner.BannerRepository
import com.example.one.model.repository.product.ProductRepository
import com.example.one.util.Variables.SORT_LATEST
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bannerRepository: BannerRepository,
    private val productRepository: ProductRepository
):NikeViewModel(){

    private val _bannerLiveData=MutableLiveData<List<Banner>>()
    val bannerLiveData:LiveData<List<Banner>> get()=_bannerLiveData

    private val _productLiveData=MutableLiveData<List<Product>>()
    val productLiveData:LiveData<List<Product>> get() = _productLiveData

    private val exceptionHandler= CoroutineExceptionHandler { coroutineContext, throwable ->
        Timber.e("get product error->$throwable")
    }


    init {
        progressBarLiveData.value=true
        getBanner()
        getProductsAndKeepFavoriteProductSign(SORT_LATEST)
//        getProduct()
    }

    private fun getBanner(){
        viewModelScope.launch {
            val banners=bannerRepository.fetchBanner()
            _bannerLiveData.postValue(banners)
            progressBarLiveData.postValue(false)
        }
    }

//    private fun getProduct(){
//        viewModelScope.launch {
//            val products=productRepository.getProducts(SORT_LATEST)
//            _productLiveData.postValue(products)
//        }
//    }

    fun onFavoriteBtnClicked(product: Product){
        viewModelScope.launch {
            if (product.isFavorite){
                productRepository.deleteFromFavorites(product)
                product.isFavorite=false
            }
            if (!product.isFavorite){
                productRepository.addToFavorites(product)
                product.isFavorite=true
            }
        }

    }

    private fun getProductsAndKeepFavoriteProductSign(sort:Int){
        viewModelScope.launch(exceptionHandler) {
            val favoriteProductsList = productRepository.getFavoriteProducts()

            val productList = productRepository.getProducts(sort)

            _productLiveData.postValue(productList)
            val favoriteProductsId = favoriteProductsList.map { product ->
                product.id
            }
            productList.forEach { product ->
                if (product.id in favoriteProductsId)
                    product.isFavorite = true
            }
        }
    }
}