package com.example.riseep3.ui.theme

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class ThemeViewModel : ViewModel() {
    private val _isDarkTheme = MutableStateFlow(false)
    open val isDarkTheme = _isDarkTheme.asStateFlow()

    open fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}
