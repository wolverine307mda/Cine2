package org.example.productos.models

import java.time.LocalDateTime
import java.util.UUID

data class Producto(
    val id: String = UUID.randomUUID().toString(),
    var nombre: String,
    var precio: Double,
    var tipo: TipoProducto?,
    var createdAt: LocalDateTime = LocalDateTime.now(),
    var updatedAt: LocalDateTime = LocalDateTime.now(),
    var isDeleted: Boolean = false
)

enum class TipoProducto (var nombre: String){
    BEBIDA("Bebida"),
    COMIDA("Comida"),
    OTROS("Otros")
}