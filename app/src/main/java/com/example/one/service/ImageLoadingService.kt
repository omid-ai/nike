package com.example.one.service

import com.facebook.drawee.view.SimpleDraweeView

interface ImageLoadingService {

    fun load(image:NikeImageView,url:String)
}