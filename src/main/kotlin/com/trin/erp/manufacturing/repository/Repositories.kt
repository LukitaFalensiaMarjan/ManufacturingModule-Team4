package com.trin.erp.manufacturing.repository

import com.trin.erp.manufacturing.database.ItemsTable
import com.trin.erp.manufacturing.database.WorkOrdersTable
import com.trin.erp.manufacturing.model.*
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

// --- Interfaces ---
interface ItemRepository {
    fun findAll(): List<Item>
    fun findById(id: String): Item?
    fun save(item: Item)
}

interface WorkOrderRepository {
    fun findAll(): List<WorkOrder>
    fun findById(id: String): WorkOrder?
    fun save(workOrder: WorkOrder)
}

// --- Database Implementations (Exposed) ---

class DatabaseItemRepository : ItemRepository {
    private fun toModel(row: ResultRow) = Item(
        id = row[ItemsTable.id],
        sku = row[ItemsTable.sku],
        name = row[ItemsTable.name],
        type = row[ItemsTable.type]
    )

    override fun findAll(): List<Item> = transaction {
        ItemsTable.selectAll().map { toModel(it) }
    }

    override fun findById(id: String): Item? = transaction {
        ItemsTable.selectAll().where { ItemsTable.id eq id }
            .map { toModel(it) }
            .singleOrNull()
    }

    override fun save(item: Item) {
        transaction {
            val exists = ItemsTable.selectAll().where { ItemsTable.id eq item.id }.count() > 0
            if (exists) {
                ItemsTable.update({ ItemsTable.id eq item.id }) {
                    it[name] = item.name
                    it[type] = item.type
                }
            } else {
                ItemsTable.insert {
                    it[id] = item.id
                    it[sku] = item.sku
                    it[name] = item.name
                    it[type] = item.type
                }
            }
        }
    }
}

class DatabaseWorkOrderRepository : WorkOrderRepository {
    private fun toModel(row: ResultRow) = WorkOrder(
        id = row[WorkOrdersTable.id],
        workOrderNumber = row[WorkOrdersTable.workOrderNumber],
        itemId = row[WorkOrdersTable.itemId],
        itemName = row[WorkOrdersTable.itemName],
        quantityToProduce = row[WorkOrdersTable.quantity],
        status = row[WorkOrdersTable.status]
    )

    override fun findAll(): List<WorkOrder> = transaction {
        WorkOrdersTable.selectAll().map { toModel(it) }
    }

    override fun findById(id: String): WorkOrder? = transaction {
        WorkOrdersTable.selectAll().where { WorkOrdersTable.id eq id }
            .map { toModel(it) }
            .singleOrNull()
    }

    override fun save(workOrder: WorkOrder) {
        transaction {
            val exists = WorkOrdersTable.selectAll().where { WorkOrdersTable.id eq workOrder.id }.count() > 0
            if (exists) {
                WorkOrdersTable.update({ WorkOrdersTable.id eq workOrder.id }) {
                    it[status] = workOrder.status
                }
            } else {
                WorkOrdersTable.insert {
                    it[id] = workOrder.id
                    it[workOrderNumber] = workOrder.workOrderNumber
                    it[itemId] = workOrder.itemId
                    it[itemName] = workOrder.itemName
                    it[quantity] = workOrder.quantityToProduce
                    it[status] = workOrder.status
                }
            }
        }
    }
}