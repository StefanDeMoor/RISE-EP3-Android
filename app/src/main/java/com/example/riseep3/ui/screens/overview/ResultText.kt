package com.example.riseep3.ui.screens.overview

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ResultText(result: Int) {
    Text(
        text = "Resultaat: €$result",
        style = MaterialTheme.typography.titleMedium
    )
}
