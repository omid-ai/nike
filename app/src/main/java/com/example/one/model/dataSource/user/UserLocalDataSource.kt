package com.example.one.model.dataSource.user

import android.content.SharedPreferences
import com.example.one.model.TokenContainer
import com.example.one.model.dataClass.MessageResponse
import com.example.one.model.dataClass.TokenResponce
import com.example.one.util.Variables.ACCESS_TOKEN
import com.example.one.util.Variables.EMAIL_KEY
import com.example.one.util.Variables.REFRESH_TOKEN
import javax.inject.Inject

class UserLocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences
) : UserDataSource {

    override suspend fun login(username: String, password: String): TokenResponce {
        TODO("Not yet implemented")
    }

    override suspend fun signUp(username: String, password: String): MessageResponse {
        TODO("Not yet implemented")
    }

    override fun loadToken() {
        TokenContainer.update(
            sharedPreferences.getString(ACCESS_TOKEN, ""),
            sharedPreferences.getString(REFRESH_TOKEN, "")
        )
    }

    override fun saveToken(token: String, refreshToken: String) {
        sharedPreferences.edit().apply {
            putString(ACCESS_TOKEN, token)
            putString(REFRESH_TOKEN, refreshToken)
        }.apply()
    }

    override fun loadEmail(): String {
        return sharedPreferences.getString(EMAIL_KEY,"")?:""
    }

    override fun saveEmail(email: String) {
        sharedPreferences.edit().putString(EMAIL_KEY,email).apply()
    }

    override fun signOut() {
        sharedPreferences.edit().apply {
            clear()
        }.apply()
        TokenContainer.remove()
    }
}