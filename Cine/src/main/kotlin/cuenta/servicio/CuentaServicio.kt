package org.example.cuenta.servicio

import com.github.michaelbull.result.Result
import org.example.butacas.errors.ButacaError
import org.example.butacas.errors.CuentaError
import org.example.butacas.models.Butaca
import org.example.cuenta.models.Cuenta

interface CuentaServicio {
    fun findAll() : Result<List<Cuenta>, CuentaError>
    fun findById(id : String) : Result<Cuenta, CuentaError>
}