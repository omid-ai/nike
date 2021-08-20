package com.example.one.model.repository.user

import com.example.one.model.TokenContainer
import com.example.one.model.dataClass.MessageResponse
import com.example.one.model.dataClass.TokenResponce
import com.example.one.model.dataSource.user.UserDataSource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val remoteDataSource:UserDataSource,
    private val localDataSource:UserDataSource
):UserRepository {

    override suspend fun login(username: String, password: String): TokenResponce =
        remoteDataSource.login(username,password)

//    override suspend fun login(username: String, password: String): TokenResponce =withContext(
//        Dispatchers.IO){
//        remoteDataSource.login(username,password)
//    }


    override suspend fun signUp(username: String, password: String): MessageResponse =
        remoteDataSource.signUp(username,password)

    override fun loadToken() {
        localDataSource.loadToken()
    }

    override suspend fun onSuccessfulLogin(tokenResponce: TokenResponce){
        localDataSource.saveToken(tokenResponce.access_token,tokenResponce.refresh_token)
        TokenContainer.update(tokenResponce.access_token,tokenResponce.refresh_token)
    }
}