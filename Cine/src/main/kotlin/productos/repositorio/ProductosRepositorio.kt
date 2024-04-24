package org.example.productos.repositorio

import org.example.productos.models.Producto

interface ProductosRepositorio {
    fun findAll(): List<Producto>
    fun findById(id: String): Producto?
    fun save(producto: Producto): Producto?
    fun update(id: String, producto: Producto): Producto?
    fun delete(id: String): Producto?
}