package com.example.riseep3.ui.componenten.category

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.example.riseep3.R
import com.example.riseep3.ui.theme.Montagu
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun NewItemDialog(
    itemName: String,
    onNameChange: (String) -> Unit,
    onConfirm: () -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        containerColor = MaterialTheme.colorScheme.onBackground,
        titleContentColor = MaterialTheme.colorScheme.surface,
        textContentColor = MaterialTheme.colorScheme.surface,

        onDismissRequest = onDismiss,

        confirmButton = {
            AnimatedDialogButton(text = "Create", onClick = onConfirm)
        },
        dismissButton = {
            AnimatedDialogButton(text = "Cancel", onClick = onDismiss)
        },
        title = {
            Text(
                text = "Create new category title",
                style = MaterialTheme.typography.headlineLarge.copy(
                    fontFamily = Montagu,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = colorResource(R.color.gold),
                    letterSpacing = 1.5.sp
                )
            )
        },
        text = {
            OutlinedTextField(
                value = itemName,
                onValueChange = onNameChange,
                label = { Text("Title") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.surface,
                    unfocusedBorderColor = MaterialTheme.colorScheme.surface,
                    focusedLabelColor = MaterialTheme.colorScheme.surface,
                    unfocusedLabelColor = MaterialTheme.colorScheme.surface,
                    cursorColor = MaterialTheme.colorScheme.surface,
                    focusedTextColor = MaterialTheme.colorScheme.surface,
                    unfocusedTextColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    )
}

@Preview(showBackground = true)
@Composable
fun NewItemDialogPreview() {
    var name by remember { mutableStateOf("Preview Item") }
    RiseTheme {
        NewItemDialog(
            itemName = name,
            onNameChange = { name = it },
            onConfirm = {},
            onDismiss = {}
        )
    }
}