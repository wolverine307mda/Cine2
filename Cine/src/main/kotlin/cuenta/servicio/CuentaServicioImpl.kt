package org.example.cuenta.servicio

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import cuenta.errors.CuentaError
import org.example.cuenta.models.Cuenta
import org.example.cuenta.repositorio.CuentaRepositorio
import org.koin.core.annotation.Singleton

@Singleton
class CuentaServicioImpl(
    private var cuentaRepositorio: CuentaRepositorio,
): CuentaServicio {
    override fun findAll(): Result<List<Cuenta>, CuentaError> {
        val result = cuentaRepositorio.findAll()
        if (result.isNotEmpty()) return Ok(result)
        else return Err(CuentaError.CuentaStorageError("No hay ninguna butaca en la base de datos"))
    }

    override fun findById(id: String): Result<Cuenta, CuentaError> {
        val cuenta = cuentaRepositorio.findById(id)
        return if (cuenta != null) {
            Ok(cuenta)
        } else {
            Err(CuentaError.CuentaNotFoundError("La butaca con ID $id no existe"))
        }
    }
}