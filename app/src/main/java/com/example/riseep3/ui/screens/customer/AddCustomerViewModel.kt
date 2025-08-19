package com.example.riseep3.ui.screens.customer

import android.net.Uri
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.riseep3.MainApplication
import com.example.riseep3.data.customer.CustomerEntity
import com.example.riseep3.data.customer.CustomerRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class AddCustomerViewModel(
    private val repository: CustomerRepository
) : ViewModel() {
    private val _state = MutableStateFlow(AddCustomerState())
    val state: StateFlow<AddCustomerState> = _state

    fun updateFirstName(value: String) {
        _state.value = _state.value.copy(firstName = value)
    }
    fun updateLastName(value: String) {
        _state.value = _state.value.copy(lastName = value)
    }
    fun updateEmail(value: String) {
        _state.value = _state.value.copy(email = value)
    }
    fun updatePhone(value: String) {
        _state.value = _state.value.copy(phone = value)
    }
    fun updateBtwNumber(value: String) {
        _state.value = _state.value.copy(btwNumber = value)
    }
    fun updateProfileImage(uri: Uri?) {
        _state.value = _state.value.copy(profileImageUri = uri)
    }

    fun saveCustomer(onSaved: () -> Unit) {
        viewModelScope.launch {
            val customer = CustomerEntity(
                firstName = _state.value.firstName,
                lastName = _state.value.lastName,
                email = _state.value.email,
                phoneNumber = _state.value.phone,
                btwNumber = _state.value.btwNumber,
                profileImagePath = _state.value.profileImageUri?.toString()
            )
            repository.insert(customer)
            onSaved()
        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val app = this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as MainApplication
                AddCustomerViewModel(app.container.customerRepository)
            }
        }
    }
}