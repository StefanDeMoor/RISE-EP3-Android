package com.example.riseep3.domain.customer

import com.example.riseep3.data.customer.CustomerEntity

fun CustomerDto.toEntity(): CustomerEntity = CustomerEntity(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email,
    phoneNumber = phoneNumber
)