package com.example.riseep3.ui.componenten.overview

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.riseep3.ui.theme.RiseTheme

@Composable
fun ResultOutlinedField(
    result: Double
) {
    OutlinedTextField(
        value = "€%.2f".format(result),
        onValueChange = {},
        label = { Text("Resultaat") },
        readOnly = true,
        singleLine = true,
        modifier = Modifier.fillMaxWidth(),
        colors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedTextColor = MaterialTheme.colorScheme.onPrimary,
            focusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedBorderColor = MaterialTheme.colorScheme.onPrimary,
            focusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            unfocusedLabelColor = MaterialTheme.colorScheme.onPrimary,
            cursorColor = MaterialTheme.colorScheme.onPrimary,
            disabledTextColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}

@Preview(showBackground = true)
@Composable
fun ResultOutlinedFieldPreview() {
    RiseTheme {
        ResultOutlinedField(
            result = 1234.56
        )
    }
}
