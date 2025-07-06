package com.example.riseep3.ui.screens.home

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

open class HomeViewModel : ViewModel() {

    val _isDarkTheme = MutableStateFlow(false)
    val isDarkTheme = _isDarkTheme.asStateFlow()

    fun toggleTheme() {
        _isDarkTheme.value = !_isDarkTheme.value
    }
}