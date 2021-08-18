package com.example.one.model.repository.banner

import com.example.one.model.dataClass.Banner
import com.example.one.model.dataSource.banner.BannerDataSource
import javax.inject.Inject

class BannerRepositoryImpl @Inject constructor(
    val remoteDataSource: BannerDataSource
): BannerRepository {

    override suspend fun fetchBanner(): List<Banner> = remoteDataSource.fetchBanner()
}