package com.example.one.di

import com.example.one.service.ImageLoadingService
import com.example.one.ui.common.ProductListAdapter
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@Module
@InstallIn(ActivityComponent::class)
object AdapterModule {

    @Provides
    fun provideProductListAdapter(imageLoadingService: ImageLoadingService):ProductListAdapter{
        return ProductListAdapter(imageLoadingService)
    }
}