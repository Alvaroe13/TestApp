package com.ai.common.compositions

import androidx.activity.ComponentActivity
import androidx.compose.runtime.compositionLocalOf

val LocalParentActivity = compositionLocalOf<ComponentActivity> {
    error("Error providing parent activity")
}