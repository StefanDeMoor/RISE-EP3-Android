package com.example.riseep3.ui.componenten.overview

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.riseep3.data.overview.OverviewEntity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import com.example.riseep3.ui.componenten.SectionHeader
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun OverviewSection(
    overviews: List<OverviewEntity>,
    createdItems: List<Pair<String, String>>,
    onAddClick: () -> Unit,
    onCardClick: (String) -> Unit
) {
    SectionHeader(
        title = "Overviews",
        onAddClick = onAddClick
    )

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
