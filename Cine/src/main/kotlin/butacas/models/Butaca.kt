package org.example.butacas.models

import java.time.LocalDateTime

data class Butaca (
    var id : String,
    var estado : Estado?,
    var ocupamiento: Ocupamiento?,
    var tipo : Tipo?,
    var createdAt : LocalDateTime = LocalDateTime.now(),
    var updatedAt : LocalDateTime = LocalDateTime.now(),
    var isDeleted : Boolean = false
){

}

enum class Estado{
    ACTIVA, EN_MANTENIMIENTO, FUERA_SERVICIO
}

enum class Ocupamiento{
    LIBRE, RESERVADA, OCUPADA
}

enum class Tipo(val precio : Int){
    VIP(8), NORMAL(5)
}