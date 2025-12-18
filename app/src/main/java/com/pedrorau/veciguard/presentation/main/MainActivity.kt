package com.pedrorau.veciguard.presentation.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.view.WindowCompat
import com.pedrorau.veciguard.presentation.theme.VeciGuardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setWindowInsets()
        enableEdgeToEdge()
        setContent {
            VeciGuardTheme {
                MainNavHost {
                    finish()
                }
            }
        }
    }

    private fun setWindowInsets() {
        WindowCompat.setDecorFitsSystemWindows(window, false)
    }
}
