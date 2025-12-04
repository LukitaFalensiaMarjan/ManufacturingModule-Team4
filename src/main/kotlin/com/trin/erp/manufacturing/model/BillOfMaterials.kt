package com.trin.erp.manufacturing.model

data class BomLine(
    val item: Item,
    val quantity: Int
)

class BillOfMaterials(
    val description: String,
    val finishedGood: Item,
    // Relasi Composition: BOM memiliki daftar komponen
    val components: MutableList<BomLine> = mutableListOf()
) : BaseEntity() {

    fun addComponent(item: Item, qty: Int) {
        if (item.type != ItemType.RAW_MATERIAL) {
            throw IllegalArgumentException("Komponen BOM harus berupa RAW_MATERIAL")
        }
        components.add(BomLine(item, qty))
    }

    override fun toString(): String {
        return "BOM for ${finishedGood.name}: $description with ${components.size} components."
    }
}