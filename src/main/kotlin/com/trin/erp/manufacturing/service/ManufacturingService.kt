package com.trin.erp.manufacturing.service

import com.trin.erp.manufacturing.model.*
import com.trin.erp.manufacturing.repository.ItemRepository
import com.trin.erp.manufacturing.repository.WorkOrderRepository
import java.util.UUID

class ManufacturingService(
    private val itemRepository: ItemRepository,
    private val workOrderRepository: WorkOrderRepository
) {

    // --- ITEM SERVICES ---
    fun getAllItems(): List<Item> = itemRepository.findAll()

    fun createItem(sku: String, name: String, type: ItemType): Item {
        val item = Item(sku = sku, name = name, type = type)
        itemRepository.save(item)
        return item
    }

    // --- WORK ORDER SERVICES ---
    fun getAllWorkOrders(): List<WorkOrder> = workOrderRepository.findAll()

    fun getWorkOrderById(id: String): WorkOrder? = workOrderRepository.findById(id)

    fun createWorkOrder(itemId: String, quantity: Int): WorkOrder {
        val item = itemRepository.findById(itemId)
            ?: throw IllegalArgumentException("Item dengan ID $itemId tidak ditemukan")

        if (item.type != ItemType.FINISHED_GOOD) {
            throw IllegalArgumentException("Hanya item tipe FINISHED_GOOD yang bisa dibuat Work Order.")
        }

        val woNumber = "WO-${UUID.randomUUID().toString().substring(0, 6).uppercase()}"

        val wo = WorkOrder(
            workOrderNumber = woNumber,
            itemId = item.id,
            itemName = item.name,
            quantityToProduce = quantity
        )

        workOrderRepository.save(wo)
        return wo
    }

    fun startProduction(woId: String): WorkOrder {
        val wo = workOrderRepository.findById(woId)
            ?: throw IllegalArgumentException("Work Order tidak ditemukan")

        wo.start() // Logic OOP
        workOrderRepository.save(wo) // Persistence
        return wo
    }

    fun completeProduction(woId: String): WorkOrder {
        val wo = workOrderRepository.findById(woId)
            ?: throw IllegalArgumentException("Work Order tidak ditemukan")

        wo.complete() // Logic OOP
        workOrderRepository.save(wo) // Persistence
        return wo
    }
}