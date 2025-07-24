package com.example.riseep3.domain.category

import com.example.riseep3.domain.overview.OverviewResponseWrapper

data class CategoryDto(
    val id: Int,
    val name: String,
    val overviews: OverviewResponseWrapper
)
