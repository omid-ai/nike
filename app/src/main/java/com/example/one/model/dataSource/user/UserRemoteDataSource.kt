package com.example.one.model.dataSource.user

import com.example.one.model.apiService.NikeApiService
import com.example.one.model.dataClass.MessageResponse
import com.example.one.model.dataClass.TokenResponce
import com.example.one.util.Variables.CLIENT_ID
import com.example.one.util.Variables.CLIENT_SECRET
import com.google.gson.JsonObject
import javax.inject.Inject

class UserRemoteDataSource @Inject constructor(
    private val apiService: NikeApiService
):UserDataSource {

    override suspend fun login(username: String, password: String): TokenResponce =
        apiService.login(JsonObject().apply {
            addProperty("username",username)
            addProperty("password",password)
            addProperty("grant_type","password")
            addProperty("client_id",CLIENT_ID)
            addProperty("client_secret",CLIENT_SECRET)
        })

    override suspend fun signUp(username: String, password: String): MessageResponse =
        apiService.signUp(JsonObject().apply {
            addProperty("email",username)
            addProperty("password",password)
        })

    override fun loadToken() {
        TODO("Not yet implemented")
    }

    override fun saveToken(token: String, refreshToken: String) {
        TODO("Not yet implemented")
    }
}