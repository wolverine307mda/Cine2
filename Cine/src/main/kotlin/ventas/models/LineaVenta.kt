package org.example.ventas.models

import org.example.productos.models.Producto
import java.time.LocalDateTime
import java.util.UUID

data class LineaVenta(
    var id : String = UUID.randomUUID().toString(),
    var producto: Producto,
    var cantidad : Int,
    var precio : Double,
    var createdAt : LocalDateTime = LocalDateTime.now(),
    var updatedAt : LocalDateTime = LocalDateTime.now(),
    var isDeleted : Boolean = false
) {
}