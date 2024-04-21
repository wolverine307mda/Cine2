package cine.app

import org.example.cuenta.servicio.CuentaServicio
import org.example.productos.servicio.ProductoServicio
import org.example.ventas.servicio.VentaServicio
import org.koin.core.component.KoinComponent

class CineApp(
    val cuentaServicio: CuentaServicio,
    val ventaService: VentaServicio,
    val productoServicio: ProductoServicio
) : KoinComponent{
}