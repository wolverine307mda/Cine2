package org.example.productos.mappers

import database.ComplementoEntity
import org.example.productos.models.Producto
import org.example.productos.models.TipoProducto
import org.example.ventas.mappers.toVenta
import java.time.LocalDateTime

fun ComplementoEntity.toProducto(): Producto {
    return Producto(
        id = this.id,
        nombre = this.nombre,
        precio = this.precio,
        tipo = elegirTipo(this.tipo),
        stock = this.stock.toInt(),
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.createdAt),
        isDeleted = this.isDeleted.toInt() == 1
    )
}

private fun elegirTipo(s: String): TipoProducto? {
    return when (s) {
        "Bebida" -> TipoProducto.BEBIDA
        "Comida" -> TipoProducto.COMIDA
        "Otros" -> TipoProducto.OTROS
        else -> null
    }
}
