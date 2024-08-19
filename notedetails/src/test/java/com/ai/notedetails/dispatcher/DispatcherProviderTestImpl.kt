package com.ai.notedetails.dispatcher

import com.ai.common_android_data.DispatcherProvider
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.test.StandardTestDispatcher

class DispatcherProviderTestImpl : DispatcherProvider {
    override fun ui(): CoroutineDispatcher = StandardTestDispatcher()
    override fun io(): CoroutineDispatcher = StandardTestDispatcher()
    override fun computation(): CoroutineDispatcher = StandardTestDispatcher()
}