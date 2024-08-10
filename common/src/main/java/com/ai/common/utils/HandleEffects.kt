package com.ai.common.utils

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import com.ai.common.viewmodel.Effect
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@Composable
fun <T: Effect> HandleEffects(
    effectFlow: Flow<T>,
    key: Any? = Unit,
    content: CoroutineScope.(effect: T) -> Unit
) {
    LaunchedEffect(
        key1 =  key,
        block =  {
            effectFlow.collect { this.content(it) }
        }
    )
}