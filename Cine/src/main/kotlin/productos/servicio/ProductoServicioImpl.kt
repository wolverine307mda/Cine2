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

    override fun findAll(): Result<List<Producto>, ProductoError> {
        val result = productosRepositorio.findAll()
        if (result.isNotEmpty()) return Ok(result)
        else return Err(ProductoError.ProductoStorageError("No hay ningún producto en la base de datos"))
    }

    override fun findById(id: String): Result<Producto, ProductoError> {
        val producto = productosRepositorio.findById(id)
        return if (producto != null) {
            Ok(producto)
        } else {
            Err(ProductoError.ProductoNotFoundError("El producto con ID $id no existe"))
        }
    }

    override fun update(id: String, producto: Producto): Result<Producto, ProductoError> {
        val existingProducto = productosRepositorio.findById(id)
        return if (existingProducto != null) {
            val updatedProducto = producto.copy(id = id)
            val result = productosRepositorio.update(id, updatedProducto)
            if (result != null) {
                Ok(updatedProducto)
            } else {
                Err(ProductoError.ProductoStorageError("No se pudo actualizar el Producto"))
            }
        } else {
            Err(ProductoError.ProductoNotFoundError("El producto con ID $id no existe"))
        }
    }
}