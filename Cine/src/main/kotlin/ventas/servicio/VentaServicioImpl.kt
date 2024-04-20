package org.example.ventas.servicio

import org.example.butacas.servicios.ButacaService
import org.example.cuenta.servicio.CuentaServicio
import org.example.ventas.respositorio.VentaRepositorio

class VentaServicioImpl (
    var butacaService: ButacaService,
    var clienteService : CuentaServicio,
    var ventaRepositorio: VentaRepositorio
) : VentaServicio {
}