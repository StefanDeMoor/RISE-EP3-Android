package com.example.riseep3

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.riseep3.ui.HomeCalcApp
import com.example.riseep3.ui.theme.RISEEP3Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            RISEEP3Theme {
                HomeCalcApp()
            }
        }
    }
}



