package org.example.butacas.models

import java.time.LocalDateTime

data class Butaca (
    var id : String, //Solo un string con ambas la fila y la columnna para que sea facil buscar
    var tipo : Tipo?,
    var estado : Estado?,
    var ocupamiento: Ocupamiento?,
    var createdAt : LocalDateTime = LocalDateTime.now(),
    var updatedAt : LocalDateTime = LocalDateTime.now(),
    var isDeleted : Boolean = false
){

}

enum class Estado (nombre : String){
    ACTIVA("Activa"),
    EN_MANTENIMIENTO("En Mantenimiento"),
    FUERA_SERVICIO("Fuera de Servicio")
}

enum class Ocupamiento(nombre : String){
    LIBRE("Libre"),
    RESERVADA("Reservada"),
    OCUPADA("Ocupada")
}

enum class Tipo(nombre : String){
    VIP("Vip"),NORMAL("Normal")
}