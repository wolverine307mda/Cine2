package org.example.butacas.mappers

import database.ButacaEntity
import org.example.butacas.models.Butaca
import org.example.butacas.models.Estado
import org.example.butacas.models.Ocupamiento
import org.example.butacas.models.Tipo
import java.time.LocalDateTime

fun ButacaEntity.toButaca() : Butaca{
    return Butaca(
        id = this.id,
        estado = elegirEstado(this.estado),
        ocupamiento = elegirOcupamiento(this.ocupamiento),
        tipo = elegirTipo(this.tipo),
        createdAt = LocalDateTime.parse(this.createdAt),
        updatedAt = LocalDateTime.parse(this.createdAt),
        isDeleted = this.isDeleted!!.toInt() == 0
    )
}

fun elegirTipo(s: String): Tipo? {
    return when(s){
        "NORMAL" -> Tipo.NORMAL
        "VIP" -> Tipo.VIP
        else -> null
    }
}

fun elegirOcupamiento(s: String): Ocupamiento? {
    return when(s){
        "OCUPADA" -> Ocupamiento.OCUPADA
        "RESERVADA" -> Ocupamiento.RESERVADA
        "LIBRE" -> Ocupamiento.LIBRE
        else -> null
    }
}

fun elegirEstado(s: String): Estado? {
    return when(s){
        "ACTIVA" -> Estado.ACTIVA
        "FUERA DE SERVICIO" -> Estado.FUERA_SERVICIO
        "EN MANTENIMIENTO" -> Estado.EN_MANTENIMIENTO
        else -> null
    }
}