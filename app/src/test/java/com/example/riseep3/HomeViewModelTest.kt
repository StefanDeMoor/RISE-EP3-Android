package com.example.riseep3

import com.example.riseep3.ui.screens.home.HomeViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Test

class HomeViewModelTest {

    @Test
    fun toggleTheme_changesThemeValue() = runBlocking {
        val viewModel = HomeViewModel()

        val initialTheme = viewModel.isDarkTheme.first()
        viewModel.toggleTheme()
        val newTheme = viewModel.isDarkTheme.first()

        assertNotEquals(initialTheme, newTheme)
    }
}
