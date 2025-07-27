package com.example.riseep3.ui.screens.overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.R
import com.example.riseep3.ui.componenten.ScreenTitle
import com.example.riseep3.ui.componenten.TopBar
import com.example.riseep3.ui.theme.ThemeViewModel

@OptIn(ExperimentalMaterial3Api::class)
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
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopBar(
                isDarkTheme = isDarkTheme,
                onToggleTheme = themeViewModel::toggleTheme,
                onNavigateBack = onNavigateBack
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            ScreenTitle("Overviews")

            if (!state.isIncomeSet) {
                OutlinedTextField(
                    value = state.income,
                    onValueChange = viewModel::onIncomeChange,
                    label = { Text("Totaal Inkomen") },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                        focusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface,
                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                        cursorColor = MaterialTheme.colorScheme.onSurface
                    ),
                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            viewModel.onIncomeConfirm()
                            keyboardController?.hide()
                        }
                    )
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Card(
                        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.onBackground),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 12.dp, vertical = 4.dp),
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
                    }

                    if (!state.isAdjusting) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Button(
                                onClick = { viewModel.onAdjustmentStart(false) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.light_red_substract))
                            ) {
                                Text("-")
                            }
                            Button(
                                onClick = { viewModel.onAdjustmentStart(true) },
                                modifier = Modifier.weight(1f),
                                colors = ButtonDefaults.buttonColors(containerColor = colorResource(R.color.light_screen_add))
                            ) {
                                Text("+")
                            }
                        }
                    }

                    Column {
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = 8.dp),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Naam", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleSmall)
                            Text("Bedrag", modifier = Modifier.weight(1f), style = MaterialTheme.typography.titleSmall)
                            Spacer(modifier = Modifier.width(64.dp))
                        }

                        HorizontalDivider()

                        state.adjustments.forEachIndexed { index, (name, value) ->
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 4.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(name, modifier = Modifier.weight(1f))
                                Text(
                                    text = "${if (value >= 0) "+" else "-"}€${kotlin.math.abs(value)}",
                                    modifier = Modifier.weight(1f)
                                )
                                Row(modifier = Modifier.width(64.dp)) {
                                    IconButton(onClick = { viewModel.onEditStart(index) }) {
                                        Icon(Icons.Default.Edit, contentDescription = "Bewerk")
                                    }
                                    IconButton(onClick = { viewModel.onDeleteAdjustment(index) }) {
                                        Icon(Icons.Default.Delete, contentDescription = "Verwijder")
                                    }
                                }
                            }
                            HorizontalDivider()
                        }

                        if (state.isAdjusting) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(vertical = 8.dp),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                TextField(
                                    value = state.amountName,
                                    onValueChange = viewModel::onAmountNameChange,
                                    placeholder = { Text("Naam") },
                                    singleLine = true,
                                    modifier = Modifier.weight(1f),
                                    colors = TextFieldDefaults.colors(
                                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                                        cursorColor = MaterialTheme.colorScheme.onSurface,
                                        focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                    )
                                )
                                Spacer(modifier = Modifier.width(8.dp))
                                TextField(
                                    value = state.amountInput,
                                    onValueChange = viewModel::onAmountChange,
                                    placeholder = { Text("€") },
                                    singleLine = true,
                                    modifier = Modifier.weight(1f),
                                    keyboardOptions = KeyboardOptions.Default.copy(imeAction = ImeAction.Done),
                                    keyboardActions = KeyboardActions(
                                        onDone = {
                                            viewModel.onAmountConfirm()
                                            keyboardController?.hide()
                                        }
                                    ),
                                    colors = TextFieldDefaults.colors(
                                        focusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedTextColor = MaterialTheme.colorScheme.onSurface,
                                        focusedContainerColor = MaterialTheme.colorScheme.surface,
                                        unfocusedContainerColor = MaterialTheme.colorScheme.surface,
                                        disabledContainerColor = MaterialTheme.colorScheme.surface,
                                        cursorColor = MaterialTheme.colorScheme.onSurface,
                                        focusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface,
                                        focusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                        unfocusedLabelColor = MaterialTheme.colorScheme.onSurface,
                                    )
                                )

                                IconButton(onClick = {
                                    viewModel.onAmountConfirm()
                                    keyboardController?.hide()
                                }) {
                                    Icon(Icons.Default.Check, contentDescription = "Bevestig")
                                }
                            }
                        }
                    }

                    Text(
                        text = "Resultaat: €${state.income.toIntOrNull() ?: 0}",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }
        }
    }
}
