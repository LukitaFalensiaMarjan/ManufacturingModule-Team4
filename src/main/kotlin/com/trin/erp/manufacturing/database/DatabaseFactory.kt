package com.trin.erp.manufacturing.database

import com.trin.erp.manufacturing.model.ItemType
import com.trin.erp.manufacturing.model.WorkOrderStatus
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

// Definisi Tabel Items (Sama seperti sebelumnya)
object ItemsTable : Table("items") {
    val id = varchar("id", 50)
    val sku = varchar("sku", 20).uniqueIndex()
    val name = varchar("name", 100)
    val type = enumerationByName("type", 20, ItemType::class)

    override val primaryKey = PrimaryKey(id)
}

// Definisi Tabel WorkOrders (Sama seperti sebelumnya)
object WorkOrdersTable : Table("work_orders") {
    val id = varchar("id", 50)
    val workOrderNumber = varchar("wo_number", 20).uniqueIndex()
    val itemId = varchar("item_id", 50) references ItemsTable.id
    val itemName = varchar("item_name", 100)
    val quantity = integer("quantity")
    val status = enumerationByName("status", 20, WorkOrderStatus::class)

    override val primaryKey = PrimaryKey(id)
}

object DatabaseFactory {
    fun init() {
        // KONFIGURASI POSTGRESQL
        // Ganti 'postgres' dan 'password' sesuai username & password pgAdmin kamu!
        val dbHost = "localhost"
        val dbPort = "8000"
        val dbName = "erp_manufacturing"
        val dbUser = "postgres" // Default user postgres
        val dbPassword = "LukiFM17092006" // <--- GANTI PASSWORD INI DENGAN PUNYAMU

        Database.connect(
            url = "jdbc:postgresql://$dbHost:$dbPort/$dbName",
            driver = "org.postgresql.Driver",
            user = dbUser,
            password = dbPassword
        )

        transaction {
            // Membuat tabel otomatis di PostgreSQL
            SchemaUtils.create(ItemsTable, WorkOrdersTable)
        }
    }
}