package com.example.riseep3.ui.screens.fake

import com.example.riseep3.ui.theme.ThemeViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeThemeViewModel : ThemeViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    override val isDarkTheme: StateFlow<Boolean> = _isDarkTheme

    override fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}