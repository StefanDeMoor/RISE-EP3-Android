package com.example.riseep3.domain.amount

import com.google.gson.annotations.SerializedName

data class AmountItemResponseWrapper(
    @SerializedName("\$values")
    val values: List<AmountItemDto>
)