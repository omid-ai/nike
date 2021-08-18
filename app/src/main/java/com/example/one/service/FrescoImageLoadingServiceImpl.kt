package com.example.one.service


class FrescoImageLoadingServiceImpl:ImageLoadingService {

    override fun load(image: NikeImageView, url: String) {
            image.setImageURI(url)
    }
}