package org.example.butacas.validator

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.butacas.errors.ButacaError
import org.example.butacas.models.Butaca
import org.koin.core.annotation.Singleton

@Singleton
class ButacaValidator {
    fun validate(butaca: Butaca): Result<Butaca, ButacaError> {
        return when {
            butaca.id.isBlank() -> Err(ButacaError.ButacaInvalida("La butaca con id: ${butaca.id} no es válida"))
            butaca.tipo == null -> Err(ButacaError.ButacaInvalida("La butaca con id: ${butaca.id} no es válida"))
            butaca.estado == null -> Err(ButacaError.ButacaInvalida("La butaca con id: ${butaca.id} no es válida"))
            butaca.ocupamiento == null -> Err(ButacaError.ButacaInvalida("La butaca con id: ${butaca.id} no es válida"))
            else -> Ok(butaca)
        }
    }

}
