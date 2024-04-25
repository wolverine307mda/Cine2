package org.example.productos.repositorio

import org.example.cuenta.mappers.toLong
import org.example.database.manager.SqlDelightManager
import org.example.database.manager.logger
import org.example.productos.mappers.toProducto
import org.example.productos.models.Producto
import org.koin.core.annotation.Singleton
import java.time.LocalDateTime

@Singleton
class ProductoRepositorioImpl(
    sqlDelightManager: SqlDelightManager
) : ProductosRepositorio {
    private val db = sqlDelightManager.databaseQueries

    override fun findAll(): List<Producto> {
        logger.debug { "Buscando todos los productos en la base de datos" }
        if (db.countProductos().executeAsOne() > 0){
            return db.getAllProductos().executeAsList().map {
                it.toProducto()
            }
        }
        return emptyList()
    }

    override fun findById(id: String): Producto? {
        logger.debug { "Buscando un producto con id: $id" }
        if (db.productoExists(id).executeAsOne()){
            return db.getProductoById(id).executeAsOne().toProducto()
        }
        return null
    }

    override fun save(producto: Producto): Producto? {
        logger.debug { "Añadiendo el producto: '${producto.nombre}' al inventario" }
        if (findById(producto.id) == null){
            db.insertComplemento(
                id = producto.id,
                nombre = producto.nombre,
                precio = producto.precio,
                stock = producto.stock.toLong(),
                tipo = producto.tipo.toString(),
                createdAt = producto.createdAt.toString(),
                updatedAt = producto.updatedAt.toString(),
                isDeleted = producto.isDeleted.toLong()
            )
            return producto
        }
        return null
    }

    override fun update(id: String, producto: Producto): Producto? {
        logger.debug { "Actualizando el producto con id: $id"}
        findById(id)?.let {
            db.updateComplemento(
                nombre = producto.nombre,
                precio = producto.precio,
                stock = producto.stock.toLong(),
                tipo = producto.tipo!!.name,
                updatedAt = LocalDateTime.now().toString(),
                isDeleted = producto.isDeleted.toLong(),
                id = id
            )
            return it.copy(
                nombre = producto.nombre,
                precio = producto.precio,
                stock = producto.stock,
                tipo = producto.tipo!!,
                updatedAt = LocalDateTime.now(),
                isDeleted = producto.isDeleted,
            )
        }
        return null
    }

    override fun delete(id: String): Producto? {
        logger.debug { "Borrando Producto con id: $id" }
        findById(id)?.let {
            db.deleteProducto(id)
            return it.copy(
                isDeleted = true,
                updatedAt = LocalDateTime.now()
            )
        }
        return null
    }

}