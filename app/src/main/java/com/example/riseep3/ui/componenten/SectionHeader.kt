package com.example.riseep3.ui.componenten

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SectionHeader(
    title: String,
    onAddClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    invertedColors: Boolean = false
) {
    val bgColor =
        if (invertedColors) MaterialTheme.colorScheme.primary
        else MaterialTheme.colorScheme.onPrimary

    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = title,
            fontWeight = FontWeight.Bold,
            fontSize = MaterialTheme.typography.titleLarge.fontSize,
            color = bgColor,
            modifier = Modifier.padding(vertical = 20.dp)
        )

        if (onAddClick != null) {
            IconButton(
                onClick = onAddClick,
                modifier = Modifier
                    .size(24.dp)
                    .testTag("${title}AddButton")
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add $title",
                    tint = bgColor
                )
            }
        }
    }
}
