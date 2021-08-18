package com.example.one.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one.model.dataClass.Banner
import com.example.one.model.dataClass.Product
import com.example.one.model.repository.banner.BannerRepository
import com.example.one.model.repository.product.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bannerRepository: BannerRepository,
    private val productRepository: ProductRepository
):ViewModel(){

    private val _bannerLiveData=MutableLiveData<List<Banner>>()
    val bannerLiveData:LiveData<List<Banner>> get()=_bannerLiveData

    private val _productLiveData=MutableLiveData<List<Product>>()
    val productLiveData:LiveData<List<Product>> get() = _productLiveData


    init {
        getBanner()
        getProduct()
    }

    private fun getBanner(){
        viewModelScope.launch {
            val banners=bannerRepository.fetchBanner()
            _bannerLiveData.postValue(banners)
        }
    }

    private fun getProduct(){
        viewModelScope.launch {
            val products=productRepository.getProducts()
            _productLiveData.postValue(products)
        }
    }
}