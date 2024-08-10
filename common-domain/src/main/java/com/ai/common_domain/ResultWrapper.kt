package com.ai.common_domain

sealed class ResultWrapper<out T> {
    data class Success<out T>(val data: T) : ResultWrapper<T>()
    data class Failure<T>(val error: ResultWrapperError) : ResultWrapper<T>()
}