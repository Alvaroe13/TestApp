package com.ai.common_domain.extentions

import com.ai.common_domain.ResultWrapper
import com.ai.common_domain.ResultWrapperError
import com.ai.common_domain.entities.NoteEntity


infix fun <T> ResultWrapper<T>.onSuccess(
    expose: (data: T) -> Unit
): ResultWrapper<*> {
    if (this is ResultWrapper.Success) {
        expose(this.data)
    }
    return this
}

infix fun <T> ResultWrapper<T>.onError(
    expose: (data: ResultWrapperError) -> Unit
): ResultWrapper<*> {
    if (this is ResultWrapper.Failure) {
        expose(this.error)
    }
    return this
}