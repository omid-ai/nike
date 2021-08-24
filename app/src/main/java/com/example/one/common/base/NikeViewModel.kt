package com.example.one.common.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class NikeViewModel:ViewModel() {

    val progressBarLiveData=MutableLiveData<Boolean>()
}