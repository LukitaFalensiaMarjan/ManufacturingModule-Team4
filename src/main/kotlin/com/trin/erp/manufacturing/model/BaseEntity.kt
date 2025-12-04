package com.trin.erp.manufacturing.model

import java.util.UUID

// Penerapan Abstraksi: Kelas dasar untuk semua entitas
abstract class BaseEntity(
    val id: String = UUID.randomUUID().toString()
)