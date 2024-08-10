package com.ai.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.ai.common.theme.TestAppTheme
import com.ai.common.compositions.LocalParentActivity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.navigation.compose.rememberNavController
import com.ai.common.utils.HandleEffects
import com.ai.main.navigation.MainNavHost
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MainScreen() }
    }
    @Composable
    private fun MainScreen() {
        TestAppTheme {
            CompositionLocalProvider (
                LocalParentActivity provides this
            ) {
                //HandleEffects(effectFlow = , content = )

                val navController = rememberNavController()
                MainNavHost(navController = navController)
            }

        }
    }
}