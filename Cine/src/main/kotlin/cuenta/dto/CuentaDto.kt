package org.example.cuenta.dto

import java.time.LocalDateTime

data class CuentaDTO(
    var nombre: String,
    var createdAt: String = LocalDateTime.now().toString(),
    var updatedAt: String = LocalDateTime.now().toString(),
    var isDeleted: Int = 0
)
