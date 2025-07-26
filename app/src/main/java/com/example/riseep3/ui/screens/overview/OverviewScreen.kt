package com.example.riseep3.ui.screens.overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.ui.componenten.TopBar
import com.example.riseep3.ui.theme.ThemeViewModel

@Composable
fun OverviewScreen(
    modifier: Modifier = Modifier,
    viewModel: OverviewViewModel = viewModel(),
    themeViewModel: ThemeViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val state = viewModel.uiState
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()
    val keyboardController = LocalSoftwareKeyboardController.current

    Scaffold(
        topBar = {
            TopBar(
                isDarkTheme = isDarkTheme,
                onToggleTheme = themeViewModel::toggleTheme,
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = modifier
                .padding(paddingValues)
                .padding(24.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.Top
        ) {
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
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Totaal Inkomen: €${state.income}",
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Row {
                            IconButton(onClick = { viewModel.onIncomeEditStart() }) {
                                Icon(Icons.Default.Edit, contentDescription = "Inkomen bewerken")
                            }
                            IconButton(onClick = { viewModel.onIncomeDelete() }) {
                                Icon(Icons.Default.Delete, contentDescription = "Inkomen verwijderen")
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
}
