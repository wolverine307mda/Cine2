package org.example.repository

import org.example.models.Complemento

interface ComplementoRepository {
    fun obtenerComplementos(): List<Complemento>
}