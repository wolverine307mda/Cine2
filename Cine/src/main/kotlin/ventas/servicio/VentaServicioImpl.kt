package org.example.ventas.servicio

import org.example.butacas.servicios.ButacaService
import org.example.butacas.storage.VentaStorage
import org.example.cuenta.servicio.CuentaServicio
import org.example.productos.servicio.ProductoServicio
import org.example.ventas.respositorio.VentaRepositorio

class VentaServicioImpl (
    var butacaService: ButacaService,
    var clienteService : CuentaServicio,
    var productoServicio: ProductoServicio,
    var ventaRepositorio: VentaRepositorio,
    var ventaStorage: VentaStorage
) : VentaServicio {

}