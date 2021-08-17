package com.example.one.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.one.model.dataClass.Banner
import com.example.one.model.repository.BannerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val bannerRepository: BannerRepository
):ViewModel(){

    private val _bannerLiveData=MutableLiveData<List<Banner>>()
    val bannerLiveData:LiveData<List<Banner>> get()=_bannerLiveData


    init {
        getBanner()
    }

    private fun getBanner(){
        viewModelScope.launch {
            val banners=bannerRepository.fetchBanner()
            _bannerLiveData.postValue(banners)
        }
    }
}