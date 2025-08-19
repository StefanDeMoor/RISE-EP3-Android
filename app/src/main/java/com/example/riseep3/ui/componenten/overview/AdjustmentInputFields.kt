package com.example.riseep3.ui.componenten.overview

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import java.util.Locale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun AdjustmentInputFields(
    amountName: String,
    onAmountNameChange: (String) -> Unit,
    amountInput: Double,
    onAmountChange: (Double) -> Unit,
    onConfirm: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current

    var inputValue by remember {
        mutableStateOf(if (amountInput == 0.0) "" else String.format(Locale.US, "%.2f", amountInput))
    }
    var nameValue by remember { mutableStateOf(amountName) }

    LaunchedEffect(amountInput, amountName) {
        inputValue = if (amountInput == 0.0) "" else String.format(Locale.US, "%.2f", amountInput)
        nameValue = amountName
    }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        TextField(
            value = nameValue,
            onValueChange = {
                nameValue = it
                onAmountNameChange(it)
            },
            placeholder = { Text("Naam") },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .testTag("AmountNameInput"),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        Spacer(modifier = Modifier.width(8.dp))
        TextField(
            value = inputValue,
            onValueChange = {
                inputValue = it
                val parsed = it.toDoubleOrNull()
                if (parsed != null) {
                    onAmountChange(parsed)
                }
            },
            placeholder = { Text("€") },
            singleLine = true,
            modifier = Modifier
                .weight(1f)
                .testTag("AmountInput"),
            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
            keyboardActions = KeyboardActions(onDone = {
                onConfirm()
                keyboardController?.hide()
            }),
            colors = TextFieldDefaults.colors(
                focusedTextColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
                focusedContainerColor = MaterialTheme.colorScheme.primary,
                unfocusedContainerColor = MaterialTheme.colorScheme.primary,
                disabledContainerColor = MaterialTheme.colorScheme.primary,
                cursorColor = MaterialTheme.colorScheme.onPrimary,
                focusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedIndicatorColor = MaterialTheme.colorScheme.onPrimary,
                focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
                unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary
            )
        )
        IconButton(onClick = {
            onConfirm()
            keyboardController?.hide()
        },
            modifier = Modifier.testTag("ConfirmButton")
        ) {
            Icon(Icons.Default.Check, contentDescription = "Bevestig")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun AdjustmentInputFieldsPreview() {
    RiseTheme {
        var name by remember { mutableStateOf("Groceries") }
        var amount by remember { mutableDoubleStateOf(12.5) }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AdjustmentInputFields(
                amountName = name,
                onAmountNameChange = { name = it },
                amountInput = amount,
                onAmountChange = { amount = it },
                onConfirm = {}
            )
        }
    }
}


