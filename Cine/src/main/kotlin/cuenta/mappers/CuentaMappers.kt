package org.example.cuenta.mappers

import org.example.cuenta.dto.CuentaDTO
import org.example.cuenta.models.Cuenta
import java.time.LocalDateTime

fun CuentaDTO.toCuenta() : Cuenta {
    return Cuenta(
        id = this.id,
        isDeleted = isDeleted == 1,
        updatedAt = LocalDateTime.parse(this.updatedAt),
        createdAt = LocalDateTime.parse(this.createdAt)
    )
}

fun Cuenta.toCuentaDto() : CuentaDTO{
    return CuentaDTO(
        id = this.id,
        createdAt = this.toString(),
        updatedAt = this.toString(),
        isDeleted = this.isDeleted.toLong().toInt()
    )
}

/**
 * Trasforma un boolean en un Long
 */
fun Boolean.toLong(): Long {
    if (this) return 1
    else return 0
}

