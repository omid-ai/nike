package com.example.one.common.exceptionHandling

import androidx.annotation.StringRes

class NikeExceptionComponents(
    val type:Type,
    @StringRes val userFriendlyMessage:Int=0,
    val serverMessage:String?=null
):Throwable() {

    enum class Type{
        AUTH,SIMPLE
    }
}