package com.trin.erp.manufacturing.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class Item(
    val id: String = UUID.randomUUID().toString(),
    val sku: String,
    val name: String,
    val type: ItemType
)