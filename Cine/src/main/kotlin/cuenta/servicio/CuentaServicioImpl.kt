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
        return Ok(cuentaRepositorio.findAll())
    }

    override fun findById(id: String): Result<Cuenta, CuentaError> {
        val cuenta = cuentaRepositorio.findById(id)
        return if (cuenta != null) {
            Ok(cuenta)
        } else {
            Err(CuentaError.CuentaNotFoundError("La cuenta con ID $id no existe"))
        }
    }

    override fun save(cuenta: Cuenta): Result<Cuenta, CuentaError> {
        cuentaRepositorio.save(cuenta)?.let {
            return Ok(it)
        }
        return Err(CuentaError.CuentaStorageError("La cuenta con id: ${cuenta.id} no se pudo guardar"))
    }


}