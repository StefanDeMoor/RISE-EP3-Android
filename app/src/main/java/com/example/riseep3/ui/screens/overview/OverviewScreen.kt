package com.example.riseep3.ui.screens.overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.ui.componenten.ScreenTitle
import com.example.riseep3.ui.componenten.TopBar
import com.example.riseep3.ui.componenten.overview.AdjustmentButtons
import com.example.riseep3.ui.componenten.overview.AdjustmentInputFields
import com.example.riseep3.ui.componenten.overview.AdjustmentList
import com.example.riseep3.ui.componenten.overview.IncomeInputField
import com.example.riseep3.ui.componenten.overview.IncomeSummaryCard
import com.example.riseep3.ui.componenten.overview.ResultOutlinedField
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
                IncomeInputField(
                    income = state.income,
                    onIncomeChange = viewModel::onIncomeChange,
                    onConfirm = viewModel::onIncomeConfirm
                )
            } else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    IncomeSummaryCard(
                        income = state.income,
                        onEdit = viewModel::onIncomeEditStart,
                        onDelete = viewModel::onIncomeDelete
                    )

                    if (!state.isAdjusting) {
                        AdjustmentButtons(
                            onSubtract = { viewModel.onAdjustmentStart(false) },
                            onAdd = { viewModel.onAdjustmentStart(true) }
                        )
                    }

                    AdjustmentList(
                        adjustments = state.adjustments,
                        onEdit = viewModel::onEditStart,
                        onDelete = viewModel::onDeleteAdjustment
                    )

                    if (state.isAdjusting) {
                        AdjustmentInputFields(
                            amountName = state.amountName,
                            onAmountNameChange = viewModel::onAmountNameChange,
                            amountInput = state.amountInput,
                            onAmountChange = viewModel::onAmountChange,
                            onConfirm = viewModel::onAmountConfirm
                        )
                    }

                    ResultOutlinedField(result = state.baseIncome - state.adjustments.sumOf { it.second })
                }
            }
        }
    }
}
