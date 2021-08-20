package com.example.one.model.dataClass

data class TokenResponce(
    val access_token: String,
    val expires_in: Int,
    val refresh_token: String,
    val token_type: String
)