package org.example.repository

import org.example.models.Venta

interface VentaRepository {
    fun realizarVenta(venta: Venta)
    fun devolverEntrada(idVenta: Int)
}