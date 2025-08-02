package com.example.riseep3.ui.screens.overview

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.riseep3.ui.componenten.ScreenTitle
import com.example.riseep3.ui.componenten.TopBar
import com.example.riseep3.ui.componenten.overview.AdjustmentButtons
import com.example.riseep3.ui.componenten.overview.AdjustmentInputFields
import com.example.riseep3.ui.componenten.overview.AdjustmentList
import com.example.riseep3.ui.componenten.overview.ResultOutlinedField
import com.example.riseep3.ui.componenten.overview.TotalIncomeInputField
import com.example.riseep3.ui.componenten.overview.TotalIncomeSummaryCard
import com.example.riseep3.ui.screens.fake.FakeAmountItemRepository
import com.example.riseep3.ui.screens.fake.FakeOverviewRepository
import com.example.riseep3.ui.screens.fake.FakeOverviewViewModel
import com.example.riseep3.ui.screens.fake.FakeThemeViewModel
import com.example.riseep3.ui.theme.ThemeViewModel

@Composable
fun OverviewScreen(
    overviewId: Int,
    modifier: Modifier = Modifier,
    viewModel: OverviewViewModel = viewModel(factory = OverviewViewModel.Factory),
    themeViewModel: ThemeViewModel,
    onNavigateBack: () -> Unit = {}
) {
    val state = viewModel.uiState
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState()

    LaunchedEffect(overviewId) {
        viewModel.loadOverviewById(overviewId)
    }

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
            ScreenTitle(state.overviewTitle)

            if (!state.isTotalIncomeSet) {
                TotalIncomeInputField(
                    totalIncome = state.totalIncome,
                    onTotalIncomeChange = viewModel::onIncomeChange,
                    onConfirm = viewModel::onIncomeConfirm
                )
            }
            else {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.fillMaxWidth()
                ) {
                    TotalIncomeSummaryCard(
                        totalIncome = state.totalIncome,
                        onEdit = viewModel::onTotalIncomeEditStart
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

                    ResultOutlinedField(result = state.result - state.adjustments.sumOf { it.third })
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun OverviewScreenPreview() {
    val fakeAmountItemRepo = remember { FakeAmountItemRepository() }
    val fakeOverviewRepo = remember { FakeOverviewRepository() }
    val fakeOverviewViewModel = remember { FakeOverviewViewModel(
        amountItemRepo = fakeAmountItemRepo,
        overviewRepo = fakeOverviewRepo
    ) }
    val fakeThemeViewModel = remember { FakeThemeViewModel() }

    OverviewScreen(
        overviewId = 1,
        viewModel = fakeOverviewViewModel,
        themeViewModel = fakeThemeViewModel,
        onNavigateBack = {}
    )
}

