package org.example.cine.servicios

import org.example.butacas.servicios.ButacaService
import org.example.cuenta.servicio.CuentaServicio
import org.example.productos.servicio.ProductoServicio
import org.example.storage.servicio.FileService
import org.example.ventas.servicio.VentaServicio

class CineService (
    val cuentaServicio: CuentaServicio,
    val ventaService: VentaServicio,
    val fileService: FileService,
    val productoServicio: ProductoServicio
){
}