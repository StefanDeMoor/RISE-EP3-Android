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
            Column {
                Text(
                    text = "Totaal Inkomen: €${state.income}",
                    style = MaterialTheme.typography.bodyLarge
                )

                Spacer(modifier = Modifier.height(8.dp))

                if (!state.isAdjusting) {
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        Button(onClick = { viewModel.onAdjustmentStart(false) }) { Text("-") }
                        Button(onClick = { viewModel.onAdjustmentStart(true) }) { Text("+") }
                    }
                } else {
                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        OutlinedTextField(
                            value = state.amountName,
                            onValueChange = { viewModel.onAmountNameChange(it) },
                            label = { Text("Naam") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = state.amountInput,
                            onValueChange = { viewModel.onAmountChange(it) },
                            label = { Text("Amount") },
                            singleLine = true,
                            keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    viewModel.onAmountConfirm()
                                    keyboardController?.hide()
                                }
                            )
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                state.adjustments.forEach { (name, value) ->
                    Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth()) {
                        Text(text = name)
                        Text(text = "${if (value >= 0) "+" else "-"}€${kotlin.math.abs(value)}")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                Text(
                    text = "Resultaat: €${state.income}",
                    style = MaterialTheme.typography.titleMedium
                )
            }
        }
    }
}



@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    val dummyState = OverviewState(
        income = "2500",
        isIncomeSet = true,
        isAdjusting = false,
        adjustments = listOf(
            "Boodschappen" to -200,
            "Extra werk" to 100
        )
    )

    MaterialTheme {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
        ) {
            Text("Overzicht", style = MaterialTheme.typography.headlineMedium)
            Spacer(modifier = Modifier.height(16.dp))

            Column {
                Text("Totaal Inkomen: €${dummyState.income}", style = MaterialTheme.typography.bodyLarge)
                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { }) { Text("-") }
                    Button(onClick = { }) { Text("+") }
                }

                Spacer(modifier = Modifier.height(16.dp))

                dummyState.adjustments.forEach { (name, adj) ->
                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(name)
                        Text("${if (adj >= 0) "+" else "-"}€${kotlin.math.abs(adj)}")
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                val result = dummyState.income.toInt() + dummyState.adjustments.sumOf { it.second }
                Text("Resultaat: €$result", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}






