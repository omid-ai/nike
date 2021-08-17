package com.example.one.model.dataSource

import com.example.one.model.apiService.NikeApiService
import com.example.one.model.dataClass.Banner
import javax.inject.Inject

class BannerRemoteDataSource @Inject constructor(
    val apiService: NikeApiService
):BannerDataSource {

    override suspend fun fetchBanner(): List<Banner> {
        return apiService.fetchBanner()
    }
}