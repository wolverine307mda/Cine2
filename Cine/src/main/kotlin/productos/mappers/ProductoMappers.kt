package org.example.productos.mappers

import database.ComplementoEntity
import org.example.productos.models.Producto
import org.example.productos.models.TipoProducto

fun ComplementoEntity.toProducto(): Producto {
    return Producto(
        id = this.id,
        nombre = this.nombre,
        precio = this.precio,
        tipo = elegirTipo(this.tipo)
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
