package com.example.riseep3.ui.componenten.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.unit.dp
import com.example.riseep3.R

@Composable
fun AdjustmentButtons(
    onSubtract: () -> Unit,
    onAdd: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Button(
            onClick = onSubtract,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.light_red_substract))
        ) { Text("-") }

        Button(
            onClick = onAdd,
            modifier = Modifier.weight(1f),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.light_screen_add))
        ) { Text("+") }
    }
}
