package com.example.riseep3.ui.componenten.overview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun AdjustmentList(
    adjustments: List<Pair<String, Double>>,
    onEdit: (Int) -> Unit,
    onDelete: (Int) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text("Naam", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleSmall)
            Text("Bedrag", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleSmall)
            Spacer(modifier = Modifier.width(64.dp))
        }

        HorizontalDivider()

        adjustments.forEachIndexed { index, (name, value) ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 4.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(name, modifier = Modifier.weight(1f))
                Text(
                    text = "${if (value <= 0) "+" else "-"}€${kotlin.math.abs(value)}",
                    modifier = Modifier.weight(1f)
                )
                Row(modifier = Modifier.width(64.dp)) {
                    IconButton(onClick = { onEdit(index) }) {
                        Icon(Icons.Default.Edit, contentDescription = "Bewerk")
                    }
                    IconButton(onClick = { onDelete(index) }) {
                        Icon(Icons.Default.Delete, contentDescription = "Verwijder")
                    }
                }
            }
            HorizontalDivider()
        }
    }
}
