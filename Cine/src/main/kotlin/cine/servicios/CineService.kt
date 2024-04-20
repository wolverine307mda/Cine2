package org.example.cine.servicios

import org.example.butacas.servicios.ButacaService
import org.example.butacas.storage.ButacaStorage
import org.example.butacas.storage.ProductoStorage
import org.example.cuenta.servicio.CuentaServicio
import org.example.productos.servicio.ProductoServicio
import org.example.ventas.servicio.VentaServicio

class CineService (
    val cuentaServicio: CuentaServicio,
    val ventaService: VentaServicio,
    val productoStorage: ProductoStorage, //Para importar los pruductos con el CSV
    val productoServicio: ProductoServicio,
    val butacaStorage: ButacaStorage //Para sacar el estado de las butacas en JSON
){
}