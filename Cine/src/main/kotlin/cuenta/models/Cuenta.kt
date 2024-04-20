package org.example.cuenta.models

import java.time.LocalDateTime

data class Cuenta (
    var nombre : String,
    var contraseña : String,
    var tipo : TipoCuenta,
    var createdAt : LocalDateTime = LocalDateTime.now(),
    var updatedAt : LocalDateTime = LocalDateTime.now(),
    var isDeleted : Boolean = false
)

enum class TipoCuenta(nombre : String){
    TRABAJADOR("Trabajador"), CLIENTE("Cliente")
}