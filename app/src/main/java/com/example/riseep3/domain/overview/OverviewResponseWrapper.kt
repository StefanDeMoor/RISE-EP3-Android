package com.example.riseep3.domain.overview

import com.google.gson.annotations.SerializedName

data class OverviewResponseWrapper(
    @SerializedName("\$values")
    val values: List<OverviewDto>
)
