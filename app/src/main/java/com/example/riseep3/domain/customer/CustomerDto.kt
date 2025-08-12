package com.example.riseep3.domain.customer

data class CustomerDto(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val email: String,
    val phoneNumber: String,
    val profileImagePath: String?
)