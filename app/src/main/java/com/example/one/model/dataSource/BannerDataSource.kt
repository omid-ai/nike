package com.example.one.model.dataSource

import com.example.one.model.dataClass.Banner

interface BannerDataSource {

    suspend fun fetchBanner():List<Banner>
}