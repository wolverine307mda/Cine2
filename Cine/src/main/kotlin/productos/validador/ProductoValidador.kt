package org.example.productos.validador

import com.github.michaelbull.result.Err
import com.github.michaelbull.result.Ok
import com.github.michaelbull.result.Result
import org.example.butacas.errors.ProductoError
import org.example.productos.models.Producto
import org.koin.core.annotation.Singleton

@Singleton
class ProductoValidador (){
    fun validate(producto: Producto) : Result<Producto, ProductoError>{
        return when{
            producto.tipo == null -> Err(ProductoError.ProductoInvalido("Categoria inválida"))
            producto.stock < 0 -> Err(ProductoError.ProductoInvalido("El stock no puede ser menos de 0"))
            producto.precio < 0 -> Err(ProductoError.ProductoInvalido("El precio no puede ser menos de 0"))
            else -> Ok(producto)
        }
    }
}