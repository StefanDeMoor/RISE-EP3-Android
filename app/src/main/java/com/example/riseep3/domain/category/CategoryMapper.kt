package com.example.riseep3.domain.category

import com.example.riseep3.data.category.CategoryEntity
import com.example.riseep3.data.overview.OverviewEntity
import com.example.riseep3.domain.overview.OverviewDto
import com.example.riseep3.domain.overview.OverviewResponseWrapper

fun CategoryDto.toEntity(): CategoryEntity =
    CategoryEntity(id = this.id, name = this.name)

fun CategoryEntity.toDto(): CategoryDto =
    CategoryDto(
        id = this.id,
        name = this.name,
        overviews = OverviewResponseWrapper(emptyList())
    )

fun OverviewDto.toEntity(categoryId: Int): OverviewEntity =
    OverviewEntity(id = this.id, title = this.title, categoryId = this.categoryId, totalIncome = this.totalIncome, result = this.result)
