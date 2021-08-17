package com.example.one.model.repository

import com.example.one.model.dataClass.Banner
import com.example.one.model.dataSource.BannerDataSource
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    val remoteDataSource: BannerDataSource
):BannerRepository {

    override suspend fun fetchBanner(): List<Banner> = remoteDataSource.fetchBanner()
}