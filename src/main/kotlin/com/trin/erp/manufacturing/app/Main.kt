package com.trin.erp.manufacturing.app

import com.trin.erp.manufacturing.database.DatabaseFactory
import com.trin.erp.manufacturing.model.Item
import com.trin.erp.manufacturing.model.ItemType
import com.trin.erp.manufacturing.repository.DatabaseItemRepository
import com.trin.erp.manufacturing.repository.DatabaseWorkOrderRepository
import com.trin.erp.manufacturing.service.ManufacturingService
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.http.content.* // PENTING: Import ini untuk melayani file HTML static
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.routing.*
import java.util.UUID

fun main() {
    // 1. Inisialisasi Database
    try {
        DatabaseFactory.init()
        println("=== Berhasil terkoneksi ke Database PostgreSQL ===")
    } catch (e: Exception) {
        println("!!! GAGAL KONEKSI DB !!!")
        println("Pesan Error: ${e.message}")
        println("Pastikan PostgreSQL sudah jalan dan konfigurasi di DatabaseFactory.kt benar.")
        return
    }

    // 2. Start Server di Port 8081
    embeddedServer(Netty, port = 8081, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    // --- Plugins ---

    // 1. JSON Serialization
    install(ContentNegotiation) {
        json()
    }

    // 2. CORS (Cross-Origin Resource Sharing)
    // Penting agar frontend (browser) bisa akses API tanpa diblokir
    install(CORS) {
        anyHost()
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Post)
        allowMethod(HttpMethod.Get)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
    }

    // --- Dependency Injection ---
    val itemRepo = DatabaseItemRepository()
    val woRepo = DatabaseWorkOrderRepository()
    val service = ManufacturingService(itemRepo, woRepo)

    // --- Data Seeding (Data Awal) ---
    try {
        if (itemRepo.findAll().isEmpty()) {
            println("Database kosong, melakukan seeding data awal...")
            itemRepo.save(Item(UUID.randomUUID().toString(), "RAW-WOOD", "Kayu Jati Grade A", ItemType.RAW_MATERIAL))
            itemRepo.save(Item(UUID.randomUUID().toString(), "RAW-NAIL", "Paku Baja 5cm", ItemType.RAW_MATERIAL))
            itemRepo.save(Item(UUID.randomUUID().toString(), "RAW-PAINT", "Cat Pernis Kayu", ItemType.RAW_MATERIAL))
            itemRepo.save(Item(UUID.randomUUID().toString(), "FG-TABLE", "Meja Makan Minimalis", ItemType.FINISHED_GOOD))
            itemRepo.save(Item(UUID.randomUUID().toString(), "FG-CHAIR", "Kursi Santai", ItemType.FINISHED_GOOD))
            println("Seeding selesai.")
        }
    } catch (e: Exception) {
        println("Warning: Gagal seeding data (Tabel mungkin belum siap atau koneksi terputus)")
    }

    // --- Routing ---
    routing {
        // 1. API Routes (Backend Logic)
        manufacturingRoutes(service)

        // 2. Static Resources (Frontend UI)
        // Ini akan mengambil file index.html dari folder src/main/resources/static
        staticResources("/", "static", index = "index.html")
    }
}