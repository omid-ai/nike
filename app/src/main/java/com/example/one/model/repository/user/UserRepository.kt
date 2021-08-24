package com.example.one.model.repository.user

import com.example.one.model.dataClass.MessageResponse
import com.example.one.model.dataClass.TokenResponce
import kotlinx.coroutines.Deferred

interface UserRepository {

    suspend fun login(username:String,password:String):TokenResponce

    suspend fun signUp(username:String,password:String):MessageResponse

    fun loadToken()

    suspend fun onSuccessfulLogin(tokenResponce: TokenResponce?,email:String?)

    fun loadEmail():String

    fun signOut()
}