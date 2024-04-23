package org.example.ventas.models

import org.example.butacas.models.Butaca
import org.example.cuenta.models.Cuenta
import java.time.LocalDateTime
import java.util.UUID

class Venta(
    var id : String = UUID.randomUUID().toString(),
    var cliente : Cuenta,
    var butaca: Butaca,
    var lineasVenta : List<LineaVenta>,
    var createdAt : LocalDateTime = LocalDateTime.now(),
    var updatedAt : LocalDateTime = LocalDateTime.now(),
    var isDeleted : Boolean = false
) {
}