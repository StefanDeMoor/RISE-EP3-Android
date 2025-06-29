package com.example.riseep3.ui.screens.overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun OverviewScreen(
    categoryName: String,
    modifier: Modifier = Modifier,
    viewModel: OverviewViewModel = viewModel()
) {
    val state = viewModel.uiState
    val keyboardController = LocalSoftwareKeyboardController.current

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top
    ) {
        Text(
            text = categoryName,
            style = MaterialTheme.typography.headlineMedium
        )

        Spacer(modifier = Modifier.height(16.dp))

        if (!state.isIncomeSet) {
            OutlinedTextField(
                value = state.income,
                onValueChange = { viewModel.onIncomeChange(it) },
                label = { Text("Totaal Inkomen") },
                singleLine = true,
                keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                keyboardActions = KeyboardActions(
                    onDone = {
                        viewModel.onIncomeConfirm()
                        keyboardController?.hide()
                    }
                )
            )
        } else {
            Text(
                text = "Totaal Inkomen: €${state.income}",
                style = MaterialTheme.typography.bodyLarge
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    val dummyState = OverviewState(income = "2500", isIncomeSet = true)

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Overzicht", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))
            Text("Totaal Inkomen: €${dummyState.income}")
        }
    }
}



