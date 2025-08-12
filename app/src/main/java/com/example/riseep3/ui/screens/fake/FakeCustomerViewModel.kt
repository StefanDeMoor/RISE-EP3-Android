package com.example.riseep3.ui.screens.fake

import android.content.Context
import android.net.Uri
import com.example.riseep3.data.customer.CustomerEntity
import com.example.riseep3.data.customer.CustomerRepository
import com.example.riseep3.ui.screens.customer.CustomerState
import com.example.riseep3.ui.screens.customer.CustomerViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class FakeCustomerViewModel(
    initialState: CustomerState,
    private val fakeRepository: CustomerRepository
) : CustomerViewModel(fakeRepository) {

    private val _fakeState = MutableStateFlow(initialState)
    override val state: StateFlow<CustomerState> = _fakeState

    var saveImageLocallyCalledWith: Uri? = null
    var updateProfileImageCalledWith: Pair<Int, String>? = null

    override fun saveImageLocally(context: Context, uri: Uri): String {
        saveImageLocallyCalledWith = uri
        return "dummy/path/to/image.jpg"
    }

    override fun updateProfileImage(id: Int, path: String) {
        updateProfileImageCalledWith = id to path
        val updatedCustomers = _fakeState.value.customers.map {
            if (it.id == id) it.copy(profileImagePath = path) else it
        }
        _fakeState.value = _fakeState.value.copy(customers = updatedCustomers)
    }

    fun setCustomers(customers: List<CustomerEntity>) {
        _fakeState.value = _fakeState.value.copy(customers = customers)
    }

    fun setLoading(isLoading: Boolean) {
        _fakeState.value = _fakeState.value.copy(isLoading = isLoading)
    }

    fun setError(error: String?) {
        _fakeState.value = _fakeState.value.copy(error = error)
    }
}
