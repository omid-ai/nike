package com.example.one.model.apiService

import com.example.one.model.dataClass.*
import com.google.gson.JsonObject
import retrofit2.http.*

interface NikeApiService {

    @GET("banner/slider")
    suspend fun fetchBanner():List<Banner>

    @GET("product/list")
    suspend fun fetchProduct(@Query("sort")sort:String):List<Product>

    @GET("comment/list")
    suspend fun fetchComments(@Query("product_id")productId:Int):List<Comment>

    @POST("cart/add")
    suspend fun addToCart(@Body jsonObject: JsonObject):AddToCartResponse

    @POST("auth/token")
    suspend fun login(@Body jsonObject: JsonObject):TokenResponce

    @POST("user/register")
    suspend fun signUp(@Body jsonObject: JsonObject):MessageResponse

    @GET("cart/list")
    suspend fun fetchCart():CartResponse
}