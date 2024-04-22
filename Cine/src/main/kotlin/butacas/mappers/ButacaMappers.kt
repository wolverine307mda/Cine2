package org.example.butacas.mappers

import database.ButacaEntity
import org.example.butacas.models.Butaca
import org.example.butacas.models.Estado
import org.example.butacas.models.Ocupamiento
import org.example.butacas.models.Tipo

fun ButacaEntity.toButaca() : Butaca{
    return Butaca(
        id = this.id,
        estado = elegirEstado(this.estado),
        ocupamiento = elegirOcupamiento(this.ocupamiento),
        tipo = elegirTipo(this.tipo)
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