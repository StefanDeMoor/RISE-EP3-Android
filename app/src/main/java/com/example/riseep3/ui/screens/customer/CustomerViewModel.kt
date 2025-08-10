package com.example.riseep3.ui.screens.customer

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.riseep3.MainApplication
import com.example.riseep3.data.customer.CustomerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.launch
import androidx.lifecycle.viewModelScope

class CustomerViewModel(
    private val repository: CustomerRepository
) : ViewModel() {

    private val _state = MutableStateFlow(CustomerState())
    val state: StateFlow<CustomerState> = _state

    init {
        loadCustomers()
    }

    private fun loadCustomers() {
        viewModelScope.launch {
            repository.getAllCustomers()
                .onStart {
                    _state.value = _state.value.copy(isLoading = true, error = null)
                }
                .catch { e ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = e.message ?: "Unknown error"
                    )
                }
                .collect { customers ->
                    _state.value = CustomerState(
                        customers = customers,
                        isLoading = false,
                        error = null
                    )
                }
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication
                CustomerViewModel(app.container.customerRepository)
            }
        }
    }
}
