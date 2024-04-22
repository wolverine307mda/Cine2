package org.example.productos.repositorio

import org.example.database.manager.SqlDelightManager
import org.example.database.manager.toLong
import org.example.productos.models.Producto
import org.koin.core.annotation.Singleton

@Singleton
class ProductoRepositorioImpl(
    sqlDelightManager: SqlDelightManager
) : ProductosRepositorio {
    private val db = sqlDelightManager.databaseQueries

    override fun findAll(): List<Producto> {
        TODO("Not yet implemented")
    }

    override fun findById(id: String): Producto? {
        TODO("Not yet implemented")
    }

    override fun save(producto: Producto): Producto? {
        db.insertComplemento(
            id = producto.id.toString(),
            nombre = producto.nombre,
            precio = producto.precio,
            tipo = producto.tipo.toString(),
            createdAt = producto.createdAt.toString(),
            updatedAt = producto.updatedAt.toString(),
            isDeleted = producto.isDeleted.toLong()
        )
        return findById(producto.id.toString())
    }

    override fun update(id: String, butaca: Producto): Producto? {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Producto? {
        TODO("Not yet implemented")
    }

}