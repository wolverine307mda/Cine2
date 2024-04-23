package org.example.productos.servicio

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import com.github.michaelbull.result.onSuccess
import org.example.butacas.errors.ProductoError
import org.example.productos.models.Producto
import org.example.productos.repositorio.ProductosRepositorio
import org.example.productos.validador.ProductoValidador
import org.koin.core.annotation.Singleton

@Singleton
class ProductoServicioImpl(
    private var productosRepositorio: ProductosRepositorio,
    private var productoValidador: ProductoValidador
) : ProductoServicio {
    override fun save(producto: Producto) : Result<Producto, ProductoError> {
        productoValidador.validate(producto) //Para esegurarse que es un producto válido
            .onSuccess {
                productosRepositorio.save(producto)?.let {
                    return Ok(producto)
                }
                return Err(ProductoError.ProductoStorageError("El producto no se pudo guardar en la base de datos"))
            }
        return Err(ProductoError.ProductoStorageError("No se pudo guardar el producto con id: ${producto.id}"))
    }
}