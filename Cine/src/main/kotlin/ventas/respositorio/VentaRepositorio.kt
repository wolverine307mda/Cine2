package org.example.ventas.respositorio

import org.example.ventas.models.Venta

interface VentaRepositorio {
    fun findAll(): List<Venta>
    fun findById(id: String): Venta?
    fun save(venta: Venta): Venta?
    fun update(id: String, venta: Venta): Venta?
    fun delete(id: String): Venta?
}