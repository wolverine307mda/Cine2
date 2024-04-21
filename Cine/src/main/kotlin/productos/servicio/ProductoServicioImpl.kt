package org.example.productos.servicio

import org.example.productos.repositorio.ProductosRepositorio
import org.koin.core.annotation.Singleton

@Singleton
class ProductoServicioImpl(
    var productosRepositorio: ProductosRepositorio
) : ProductoServicio {
}