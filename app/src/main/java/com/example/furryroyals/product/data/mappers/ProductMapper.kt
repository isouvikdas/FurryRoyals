package com.example.furryroyals.product.data.mappers

import com.example.furryroyals.product.data.local.ProductEntity
import com.example.furryroyals.product.data.remote.dto.ProductDto
import com.example.furryroyals.product.domain.Product

fun ProductDto.toProductEntity(): ProductEntity {
    return ProductEntity(
        id = id,
        name = name,
        price = price,
        description = description,
        categoryName = categoryName,
        inventory = inventory,
        firstImageId = firstImageId,
        imageIds = imageIds
    )
}

fun ProductEntity.toProduct(): Product {
    return Product(
        localId = localId,
        id = id,
        name = name,
        price = price,
        description = description,
        categoryName = categoryName,
        inventory = inventory,
        firstImageId = firstImageId,
        imageIds = imageIds,
        firstImageUri = this.firstImageUri
    )
}