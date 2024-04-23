package org.example.productos.servicio

import com.github.michaelbull.result.Result
import org.example.butacas.errors.ProductoError
import org.example.productos.models.Producto

interface ProductoServicio {
    fun save(producto: Producto) : Result<Producto, ProductoError>
}