package com.example.one.model.repository.banner

import com.example.one.model.dataClass.Banner

interface BannerRepository {

    suspend fun fetchBanner():List<Banner>
}