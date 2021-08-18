package com.example.one.model.apiService

import com.example.one.model.dataClass.Banner
import com.example.one.model.dataClass.Comment
import com.example.one.model.dataClass.Product
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface NikeApiService {

    @GET("banner/slider")
    suspend fun fetchBanner():List<Banner>

    @GET("product/list")
    suspend fun fetchProduct():List<Product>

    @GET("comment/list")
    suspend fun fetchComments(@Query("product_id")productId:Int):List<Comment>
}