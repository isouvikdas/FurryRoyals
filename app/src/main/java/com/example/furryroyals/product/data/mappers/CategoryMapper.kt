package com.example.furryroyals.product.data.mappers

import com.example.furryroyals.product.data.local.CategoryEntity
import com.example.furryroyals.product.data.remote.dto.CategoryDto
import com.example.furryroyals.product.domain.Category

fun CategoryDto.toCategoryEntity(): CategoryEntity {
    return CategoryEntity(
        id = id,
        name = name,
        productIds = productIds
    )
}

fun CategoryEntity.toCategory(): Category {
    return Category(
        id = id,
        name = name,
        productIds = productIds,
        localId = localId
    )
}