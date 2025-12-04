package com.trin.erp.manufacturing.model

import kotlinx.serialization.Serializable

@Serializable
enum class ItemType {
    RAW_MATERIAL,
    FINISHED_GOOD
}

@Serializable
enum class WorkOrderStatus {
    PENDING,
    IN_PROGRESS,
    COMPLETED,
    CANCELLED
}