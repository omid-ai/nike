package com.example.one.model.apiService

import com.example.one.model.dataClass.Banner
import retrofit2.http.GET

interface NikeApiService {

    @GET("banner/slider")
    suspend fun fetchBanner():List<Banner>
}