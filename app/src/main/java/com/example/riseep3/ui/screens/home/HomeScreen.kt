package com.example.riseep3.ui.screens.home

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material.icons.filled.Face
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCreateClick: () -> Unit
) {
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Welcome!", style = MaterialTheme.typography.headlineLarge)
        Spacer(modifier = Modifier.height(32.dp))
        Row(horizontalArrangement = Arrangement.spacedBy(24.dp)) {
            IconButton(
                onClick = onCreateClick,
                modifier = Modifier.size(96.dp).aspectRatio(1f)
            ) {
                Icon(Icons.Default.Create, contentDescription = "Create", modifier = Modifier.fillMaxSize(0.6f))
            }
            IconButton(
                onClick = { /* Profile actie */ },
                modifier = Modifier.size(96.dp).aspectRatio(1f)
            ) {
                Icon(Icons.Default.Face, contentDescription = "Profile", modifier = Modifier.fillMaxSize(0.6f))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    HomeScreen(onCreateClick = {})
}
