package com.example.riseep3.viewmodels

import com.example.riseep3.ui.theme.ThemeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class HomeViewModelTest {

    @Test
    fun toggleTheme_changesThemeValue() = runBlocking {
        val viewModel = ThemeViewModel()

        val initialTheme = viewModel.isDarkTheme.first()
        viewModel.toggleTheme()
        val newTheme = viewModel.isDarkTheme.first()

        assertNotEquals(initialTheme, newTheme)
    }
}
