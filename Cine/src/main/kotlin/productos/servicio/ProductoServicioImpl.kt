package org.example.productos.servicio

import org.example.productos.repositorio.ProductosRepositorio

class ProductoServicioImpl(
    var productosRepositorio: ProductosRepositorio
) : ProductoServicio {
}