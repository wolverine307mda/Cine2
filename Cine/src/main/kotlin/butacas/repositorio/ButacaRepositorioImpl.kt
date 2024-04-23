package org.example.butacas.repositorio

import org.example.butacas.mappers.toButaca
import org.example.butacas.models.Butaca
import org.example.database.manager.SqlDelightManager
import org.example.database.manager.logger
import org.example.database.manager.toLong
import org.koin.core.annotation.Singleton
import java.time.LocalDateTime

@Singleton
class ButacaRepositorioImpl (
    sqlDelightManager: SqlDelightManager
) : ButacaRepositorio{
    private val db = sqlDelightManager.databaseQueries

    override fun findAll(): List<Butaca> {
        logger.debug { "Buscando todas las butacas en la base de datos" }
        if (db.countButacas().executeAsOne() > 0){
            return db.getAllButacas().executeAsList().map {
                it.toButaca()
            }
        }
        return emptyList()
    }

    override fun findById(id: String): Butaca? {
        logger.debug { "Buscando una butaca con id: $id" }
        if (db.butacaExists(id).executeAsOne()){
            return db.getButacaById(id).executeAsOne().toButaca()
        }
        return null
    }

    override fun save(butaca: Butaca): Butaca? {
        logger.debug { "Buscado la butaca con id: ${butaca.id}" }
        if (findById(butaca.id) == null){
            db.insertButaca(
                id = butaca.id.uppercase(),
                tipo = butaca.tipo!!.name,
                estado = butaca.estado!!.name,
                ocupamiento = butaca.ocupamiento!!.name,
                createdAt = LocalDateTime.now().toString(),
                updatedAt = LocalDateTime.now().toString(),
                isDeleted = butaca.isDeleted.toLong()
            )
            return butaca
        }
        return null
    }

    override fun update(id: String, butaca: Butaca): Butaca? {
        logger.debug { "Actualizando la butaca con id: $id"}
        findById(id)?.let {
            db.updateButaca(
                ocupamiento = butaca.ocupamiento!!.name,
                tipo = butaca.tipo!!.name,
                estado = butaca.estado!!.name,
                updatedAt = LocalDateTime.now().toString(),
                isDeleted = butaca.isDeleted.toLong(),
                id = id
            )
        }
        return null
    }

    override fun delete(id: String): Butaca? {
        logger.debug { "Borrando butaca con id: $id" }
        findById(id)?.let {
            db.deleteButaca(id)
        }
        return null
    }

}
