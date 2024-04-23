package org.example.ventas.respositorio

import org.example.butacas.repositorio.ButacaRepositorio
import org.example.cuenta.repositorio.CuentaRepositorio
import org.example.database.manager.SqlDelightManager
import org.example.database.manager.logger
import org.example.database.manager.toLong
import org.example.productos.repositorio.ProductosRepositorio
import org.example.ventas.mappers.toLineaVenta
import org.example.ventas.mappers.toVenta
import org.example.ventas.models.LineaVenta
import org.example.ventas.models.Venta
import org.koin.core.annotation.Singleton
import java.time.LocalDateTime

@Singleton
class VentaRepositorioImpl(
    private val sqlDelightManager: SqlDelightManager,
    private val productosRepositorio: ProductosRepositorio,
    private val clienteRepositorio : CuentaRepositorio,
    private val butacaRepositorio: ButacaRepositorio
) : VentaRepositorio{

    private var db = sqlDelightManager.databaseQueries

    override fun findAll(): List<Venta> {
        logger.debug { "Buscando todas las ventas en la base de datos" }
        if (db.countVentas().executeAsOne() > 0){ //Para evitar que executeAsList te de una excepcion
            return db
                .getAllVentas()
                .executeAsList()
                .map{
                    val lineas =  getAllLineasByVentaId(it.id)
                    val butaca = butacaRepositorio.findById(it.id_butaca)
                    val cliente = clienteRepositorio.findById(it.id_socio)
                    it.toVenta(lineas = lineas, butaca = butaca!!, cliente = cliente!!)
                }
        }
        return emptyList()
    }

    private fun getAllLineasByVentaId(id : String) : List<LineaVenta>{
        if (db.countLineasVenta(id).executeAsOne() > 0){
            db.getLineaVentaByVentaId(id)
                .executeAsList()
                .map {
                    val producto = productosRepositorio.findById(it.id_complemento)
                    it.toLineaVenta(producto!!)
                }
        }
        return emptyList()
    }

    override fun findById(id: String): Venta? {
        logger.debug { "Buscando la venta con id : $id" }
        if (db.existsVenta(id).executeAsOne()){
            val ventaEntity = db.getVentaById(id).executeAsOne()
            val lineas = getAllLineasByVentaId(ventaEntity.id)
            val butaca = butacaRepositorio.findById(ventaEntity.id)
            val cliente = clienteRepositorio.findById(ventaEntity.id_socio)
            return ventaEntity.toVenta(lineas,butaca!!, cliente!!)
        }
        return null
    }

    override fun save(venta: Venta): Venta? {
        logger.debug { "Guardando venta con id: ${venta.id}" }
        if (findById(venta.id) == null){
            db.insertVenta(
                id = venta.id,
                id_socio = venta.cliente.id,
                id_butaca = venta.butaca.id,
                updatedAt = venta.updatedAt.toString(),
                createdAt = venta.createdAt.toString(),
                isDeleted = venta.isDeleted.toLong()
            )
            venta.lineasVenta.forEach {
                db.insertLineaVenta(
                    id = it.id,
                    id_venta = venta.id,
                    id_complemento = it.producto.id,
                    precio = it.precio,
                    cantidad = it.cantidad.toLong(),
                    createdAt = it.createdAt.toString(),
                    updatedAt = it.updatedAt.toString(),
                    isDeleted = it.isDeleted.toLong()
                )
            }
            return venta
        }
        return null
    }

    override fun update(id: String, venta: Venta): Venta? {
        logger.debug { "Actualizando venta con id: $id" }
        findById(id)?.let { //Si existe
            db.updateVenta(
                id_butaca = venta.butaca.id,
                id_socio = venta.cliente.id,
                isDeleted = venta.isDeleted.toLong(),
                updatedAt = LocalDateTime.now().toString(),
                id = id
            )
            venta.lineasVenta.forEach {
                updateLineasVenta(id, it)
            }
        }
        return null
    }

    private fun updateLineasVenta(id : String, lineaVenta: LineaVenta){
        db.updateLineaVenta(
            id_complemento = lineaVenta.id,
            isDeleted = lineaVenta.isDeleted.toLong(),
            cantidad = lineaVenta.cantidad.toLong(),
            precio = lineaVenta.precio,
            updatedAt = LocalDateTime.now().toString(),
            id = id
        )
    }

    override fun delete(id: String): Venta? {
        logger.debug { "Borrando venta con id: $id" }
        findById(id)?.let { //Si existe
            db.deleteVenta(id)
            it.lineasVenta.forEach {
                db.deleteLineaVenta(it.id)
            }
        }
        return null
    }
}