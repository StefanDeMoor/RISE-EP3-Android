package com.example.riseep3.ui.componenten.overview


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.example.riseep3.data.overview.OverviewEntity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun OverviewSection(
    overviews: List<OverviewEntity>,
    createdItems: List<Pair<String, String>>,
    onAddClick: () -> Unit,
    onCardClick: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Overviews",
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = MaterialTheme.colorScheme.onSurface
        )

        IconButton(
            onClick = onAddClick,
            modifier = Modifier.size(24.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add Overview",
                tint = MaterialTheme.colorScheme.onSurface
            )
        }
    }

    overviews.forEach { overview ->
        OverviewCard(title = overview.title, onClick = { onCardClick(overview.id.toString()) })
    }

    createdItems.filter { it.second.equals("Overview", ignoreCase = true) }
        .forEach { (itemName, _) ->
            OverviewCard(title = itemName, onClick = { onCardClick(itemName) })
        }
}

@Preview(showBackground = true)
@Composable
fun OverviewSectionPreview() {
    val mockOverviews = listOf(
        OverviewEntity(
            id = 1, title = "Budget Q1",
            categoryId = 1,
            totalIncome = 2100.00,
            result = 0.0
        ),
        OverviewEntity(
            id = 2, title = "Holiday Fund",
            categoryId = 1,
            totalIncome = 500.00,
            result = 0.0
        )
    )
    val mockCreatedItems = listOf(
        "Savings" to "Overview",
        "Investments" to "Overview"
    )

    RiseTheme {
        Column(modifier = Modifier.padding(16.dp)) {
            OverviewSection(
                overviews = mockOverviews,
                createdItems = mockCreatedItems,
                onAddClick = {},
                onCardClick = {}
            )
        }
    }
}
