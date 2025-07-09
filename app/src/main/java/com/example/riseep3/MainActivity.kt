package com.example.riseep3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.*
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.ui.HomeCalcApp
import com.example.riseep3.ui.screens.home.HomeViewModel
import com.example.riseep3.ui.theme.RiseTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HomeCalcApp()
        }
    }
}

