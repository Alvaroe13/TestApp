package com.ai.common_android_data

import kotlinx.coroutines.CoroutineDispatcher

interface DispatcherProvider {
    fun ui(): CoroutineDispatcher
    fun io(): CoroutineDispatcher
    fun computation(): CoroutineDispatcher
}
