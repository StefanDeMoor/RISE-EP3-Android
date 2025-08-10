package com.example.riseep3.ui.screens.customer

import com.example.riseep3.data.customer.CustomerEntity

data class CustomerState(
    val customers: List<CustomerEntity> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)