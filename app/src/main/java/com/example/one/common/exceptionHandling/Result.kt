package com.example.one.common.exceptionHandling

sealed class Result<T>(
    val data: T? = null,
    val message: String? = null,
) {
    class Success<T>(data: T?, message: String?) : Result<T>(data, message)
    class Error<T>(data: T?, message: String?) : Result<T>(data, message)
    class EmptyState<T> : Result<T>()
}