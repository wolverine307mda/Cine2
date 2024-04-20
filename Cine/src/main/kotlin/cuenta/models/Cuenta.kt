package org.example.cuenta.models

import java.time.LocalDateTime

data class Cuenta (
    var nombre : String,
    var createdAt : LocalDateTime = LocalDateTime.now(),
    var updatedAt : LocalDateTime = LocalDateTime.now(),
    var isDeleted : Boolean = false
)