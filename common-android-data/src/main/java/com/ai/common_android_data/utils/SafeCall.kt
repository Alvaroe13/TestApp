package com.ai.common_android_data.utils

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.ResultWrapperError
import java.net.SocketTimeoutException

suspend fun <T> safeCall(call: suspend () -> T): ResultWrapper<T> {
    return try {
        val result = call()
        ResultWrapper.Success(result)
    } catch (throwable: Throwable) {
        // here we can log issue with Timber or any other logger
        when (throwable) {
            is SocketTimeoutException -> ResultWrapper.Failure(ResultWrapperError.Timeout)
            else -> ResultWrapper.Failure(ResultWrapperError.Unknown)
        }
    }
}