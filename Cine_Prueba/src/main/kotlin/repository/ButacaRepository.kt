package org.example.repository

import org.example.models.Butaca

interface ButacaRepository {
    fun obtenerButacasDisponibles(): List<Butaca>
    fun reservarButaca(idButaca: Int, idSocio: String, fechaCompra: String)
}