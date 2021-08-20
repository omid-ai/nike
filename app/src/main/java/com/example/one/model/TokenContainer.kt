package com.example.one.model

import timber.log.Timber

object TokenContainer {

    var token:String?=null
    private set

    var refreshToken:String?=null
    private set

    fun update(token:String?,refreshToken:String?){
        Timber.i("this is token-> $token")
        Timber.i("this is refreshToken-> $refreshToken")
        this.token=token
        this.refreshToken=refreshToken
    }
}