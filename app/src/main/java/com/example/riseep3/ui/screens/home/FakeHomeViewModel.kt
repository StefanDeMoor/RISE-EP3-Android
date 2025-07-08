package com.example.riseep3.ui.screens.home

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeHomeViewModel : HomeViewModel() {

    private val _fakeDarkTheme = MutableStateFlow(false)
    override val isDarkTheme: StateFlow<Boolean>
        get() = _fakeDarkTheme

    override fun toggleTheme() {
        _fakeDarkTheme.value = !_fakeDarkTheme.value
    }
}