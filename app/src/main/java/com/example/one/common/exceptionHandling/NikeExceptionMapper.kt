package com.example.one.common.exceptionHandling

import com.example.one.R
import org.json.JSONObject
import retrofit2.HttpException

class NikeExceptionMapper {

    companion object {

        fun mapper(throwable: Throwable): NikeExceptionComponents {
            if (throwable is HttpException) {
                val jsonObject = JSONObject(throwable.response()?.errorBody()!!.string())
                val errorMessage = jsonObject.getString("message")
                return NikeExceptionComponents(
                    if (throwable.code() != 401) NikeExceptionComponents.Type.SIMPLE else NikeExceptionComponents.Type.AUTH,
                    serverMessage = errorMessage
                )
            }
            return NikeExceptionComponents(NikeExceptionComponents.Type.SIMPLE, R.string.unknown_Error)
        }
    }
}