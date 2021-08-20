package com.example.one.model.dataSource.user

import com.example.one.model.dataClass.MessageResponse
import com.example.one.model.dataClass.TokenResponce

interface UserDataSource {

    suspend fun login(username:String,password:String):TokenResponce

    suspend fun signUp(username:String,password:String): MessageResponse

    fun loadToken()

    fun saveToken(token:String,refreshToken:String)
}