package org.example.butacas.mappers

import database.ButacaEntity
import org.example.butacas.models.Butaca
import org.example.butacas.models.Estado
import org.example.butacas.models.Ocupamiento
import org.example.butacas.models.Tipo
import org.example.ventas.mappers.toVenta
import java.time.LocalDateTime

fun ButacaEntity.toButaca() : Butaca{
    return Butaca(
        id = this.id,
        estado = elegirEstado(this.estado),
        ocupamiento = elegirOcupamiento(this.ocupamiento),
        tipo = elegirTipo(this.tipo),
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.createdAt),
        isDeleted = this.isDeleted!!.toInt() == 1
    )
}

fun elegirTipo(s: String): Tipo? {
    return when(s){
        "Normal" -> Tipo.NORMAL
        "VIP" -> Tipo.VIP
        else -> null
    }
}

fun elegirOcupamiento(s: String): Ocupamiento? {
    return when(s){
        "Ocupada" -> Ocupamiento.OCUPADA
        "Reservada" -> Ocupamiento.RESERVADA
        "Libre" -> Ocupamiento.LIBRE
        else -> null
    }
}

fun elegirEstado(s: String): Estado? {
    return when(s){
        "Activa" -> Estado.ACTIVA
        "Fuera de Servicio" -> Estado.FUERA_SERVICIO
        "En Mantenimiento" -> Estado.EN_MANTENIMIENTO
        else -> null
    }
}