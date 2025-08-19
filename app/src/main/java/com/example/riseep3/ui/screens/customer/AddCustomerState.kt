package com.example.riseep3.ui.screens.customer

import android.net.Uri

data class AddCustomerState(
    val firstName: String = "",
    val lastName: String = "",
    val email: String = "",
    val phone: String = "",
    val btwNumber: String = "",
    val profileImageUri: Uri? = null
)