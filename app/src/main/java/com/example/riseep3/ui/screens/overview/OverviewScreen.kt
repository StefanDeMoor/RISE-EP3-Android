package com.example.riseep3.ui.screens.overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
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
                onValueChange = viewModel::onIncomeChange,
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
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = "Totaal Inkomen: €${state.income}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Row {
                            IconButton(onClick = {
                                viewModel.onIncomeEditStart()
                            }) {
                                Icon(Icons.Default.Edit, contentDescription = "Inkomen bewerken")
                            }
                            IconButton(onClick = {
                                viewModel.onIncomeDelete()
                            }) {
                                Icon(Icons.Default.Delete, contentDescription = "Inkomen verwijderen")
                            }
                        }
                    }

                }

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
                            onValueChange = viewModel::onAmountNameChange,
                            label = { Text("Naam") },
                            singleLine = true
                        )
                        OutlinedTextField(
                            value = state.amountInput,
                            onValueChange = viewModel::onAmountChange,
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

                state.adjustments.forEachIndexed { index, (name, value) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(name)
                        Row {
                            Text("${if (value >= 0) "+" else "-"}€${kotlin.math.abs(value)}")
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(onClick = { viewModel.onEditStart(index) }) {
                                Icon(Icons.Default.Edit, contentDescription = "Bewerk")
                            }
                            IconButton(onClick = { viewModel.onDeleteAdjustment(index) }) {
                                Icon(Icons.Default.Delete, contentDescription = "Verwijder")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                val result = state.income.toIntOrNull() ?: 0
                Text(
                    text = "Resultaat: €$result",
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
                // Totaal Inkomen + iconen
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Text("Totaal Inkomen: €${dummyState.income}", style = MaterialTheme.typography.bodyLarge)
                    Row {
                        IconButton(onClick = { /* Bewerken voorbeeld */ }) {
                            Icon(Icons.Default.Edit, contentDescription = "Bewerk Inkomen")
                        }
                        IconButton(onClick = { /* Verwijderen voorbeeld */ }) {
                            Icon(Icons.Default.Delete, contentDescription = "Verwijder Inkomen")
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                    Button(onClick = { }) { Text("-") }
                    Button(onClick = { }) { Text("+") }
                }

                Spacer(modifier = Modifier.height(16.dp))

                dummyState.adjustments.forEach { (name, adj) ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 4.dp),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(name)
                        Row {
                            Text("${if (adj >= 0) "+" else "-"}€${kotlin.math.abs(adj)}")
                            Spacer(modifier = Modifier.width(8.dp))
                            IconButton(onClick = { /* Bewerken voorbeeld */ }) {
                                Icon(Icons.Default.Edit, contentDescription = "Bewerk")
                            }
                            IconButton(onClick = { /* Verwijderen voorbeeld */ }) {
                                Icon(Icons.Default.Delete, contentDescription = "Verwijder")
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(24.dp))
                val result = dummyState.income.toInt() + dummyState.adjustments.sumOf { it.second }
                Text("Resultaat: €$result", style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}







