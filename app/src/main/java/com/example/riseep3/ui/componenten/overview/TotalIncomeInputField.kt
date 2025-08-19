package com.example.riseep3.ui.componenten.overview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun TotalIncomeInputField(
    totalIncome: Double,
    onTotalIncomeChange: (Double) -> Unit,
    onConfirm: () -> Unit
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    val inputState = remember {
        mutableStateOf(if (totalIncome == 0.0) "" else totalIncome.toString())
    }

    OutlinedTextField(
        value = inputState.value,
        onValueChange = { newText ->
            inputState.value = newText
            newText.toDoubleOrNull()?.let { onTotalIncomeChange(it) }
        },
        label = { Text("Totaal Inkomen") },
        singleLine = true,
        modifier = Modifier
            .fillMaxWidth()
            .testTag("TotalIncomeInputField"),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.onPrimary
        ),
        keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
        keyboardActions = KeyboardActions(onDone = {
            onConfirm()
            keyboardController?.hide()
        })
    )
}

@Preview(showBackground = true)
@Composable
fun TotalIncomeInputFieldPreview() {
    RiseTheme {
        val totalIncomeState = remember { mutableDoubleStateOf(2500.0) }

        TotalIncomeInputField(
            totalIncome = totalIncomeState.doubleValue,
            onTotalIncomeChange = { totalIncomeState.doubleValue = it },
            onConfirm = {}
        )
    }
}

