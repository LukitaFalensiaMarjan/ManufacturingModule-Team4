package com.trin.erp.manufacturing.model

import kotlinx.serialization.Serializable
import java.util.UUID

@Serializable
data class WorkOrder(
    val id: String = UUID.randomUUID().toString(),
    val workOrderNumber: String,
    val itemId: String, // Kita simpan ID itemnya saja (Relational DB style)
    val itemName: String, // (Opsional) Snapshot nama item saat WO dibuat
    val quantityToProduce: Int,
    var status: WorkOrderStatus = WorkOrderStatus.PENDING
) {
    // Domain Logic tetap ada di sini (OOP Encapsulation)
    fun start() {
        if (status == WorkOrderStatus.PENDING) {
            status = WorkOrderStatus.IN_PROGRESS
        } else {
            throw IllegalStateException("Hanya WO 'PENDING' yang bisa dimulai.")
        }
    }

    fun complete() {
        if (status == WorkOrderStatus.IN_PROGRESS) {
            status = WorkOrderStatus.COMPLETED
        } else {
            throw IllegalStateException("Hanya WO 'IN_PROGRESS' yang bisa diselesaikan.")
        }
    }
}