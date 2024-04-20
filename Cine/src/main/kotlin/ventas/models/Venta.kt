package org.example.ventas.models

import org.example.cuenta.models.Cuenta
import java.time.LocalDateTime
import java.util.UUID

class Venta(
    var id : UUID = UUID.randomUUID(),
    var cliente : Cuenta,
    var cantidad : Int,
    var lineasVenta : List<LineaVenta>,
    var createdAt : LocalDateTime = LocalDateTime.now(),
    var updatedAt : LocalDateTime = LocalDateTime.now(),
    var isDeleted : Boolean = false

) {
}