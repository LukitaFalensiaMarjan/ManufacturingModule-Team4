package com.trin.erp.manufacturing.app

import com.trin.erp.manufacturing.model.ItemType
import com.trin.erp.manufacturing.service.ManufacturingService
import io.ktor.http.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.serialization.Serializable

fun Route.manufacturingRoutes(service: ManufacturingService) {

    route("/api") {

        // GET all items
        get("/items") {
            call.respond(service.getAllItems())
        }

        // POST create item (Opsional, buat test)
        post("/items") {
            @Serializable data class CreateItemRequest(val sku: String, val name: String, val type: ItemType)
            val req = call.receive<CreateItemRequest>()
            try {
                val item = service.createItem(req.sku, req.name, req.type)
                call.respond(HttpStatusCode.Created, item)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // GET all work orders
        get("/work-orders") {
            call.respond(service.getAllWorkOrders())
        }

        // GET detail work order
        get("/work-orders/{id}") {
            val id = call.parameters["id"]
            val wo = id?.let { service.getWorkOrderById(it) }
            if (wo != null) {
                call.respond(wo)
            } else {
                call.respond(HttpStatusCode.NotFound, mapOf("error" to "Work Order not found"))
            }
        }

        // POST create work order
        post("/work-orders") {
            @Serializable data class CreateWORequest(val itemId: String, val quantity: Int)
            val req = call.receive<CreateWORequest>()

            try {
                val wo = service.createWorkOrder(req.itemId, req.quantity)
                call.respond(HttpStatusCode.Created, wo)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // POST start production
        post("/work-orders/{id}/start") {
            val id = call.parameters["id"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            try {
                val wo = service.startProduction(id)
                call.respond(HttpStatusCode.OK, wo)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }

        // POST complete production
        post("/work-orders/{id}/complete") {
            val id = call.parameters["id"] ?: return@post call.respond(HttpStatusCode.BadRequest)
            try {
                val wo = service.completeProduction(id)
                call.respond(HttpStatusCode.OK, wo)
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest, mapOf("error" to e.message))
            }
        }
    }
}