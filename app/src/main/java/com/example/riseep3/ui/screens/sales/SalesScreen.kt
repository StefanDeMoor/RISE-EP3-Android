package com.example.riseep3.ui.screens.sales

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*

@Composable
fun SalesScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(text = "Sales")
    }
}

@Preview(showBackground = true)
@Composable
fun SalesScreenPreview() {
    SalesScreen()
}