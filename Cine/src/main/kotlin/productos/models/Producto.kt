package org.example.productos.models

import java.time.LocalDateTime
import java.util.UUID

data class Producto (
    val id : UUID = UUID.randomUUID(),
    var nombre : String,
    var stock : Int,
    var tipo : TipoProducto,
    var createdAt : LocalDateTime = LocalDateTime.now(),
    var updatedAt : LocalDateTime = LocalDateTime.now(),
    var isDeleted : Boolean = false
)

enum class TipoProducto (var nombre: String){
    BEBIDA("Bebida"),
    COMIDA("Comida"),
    OTROS("Otros")
}