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
        return db.getAllButacas().executeAsList().map {
            it.toButaca()
        }
    }

    override fun findById(id: String): Butaca? {
        return db.getButacaById(id).executeAsOne().toButaca()
    }

    override fun save(butaca: Butaca): Butaca? {
        db.insertButaca(
            id = butaca.id.uppercase(),
            tipo = butaca.tipo!!.name,
            estado = butaca.estado!!.name,
            ocupamiento = butaca.ocupamiento!!.name,
            createdAt = LocalDateTime.now().toString(),
            updatedAt = LocalDateTime.now().toString(),
            isDeleted = butaca.isDeleted.toLong() )
        logger.debug { "Añadida la butaca con id: ${butaca.id}" }
        return try {
            if (db.selectLastButacaInserted().executeAsOne().id != butaca.id){
                logger.debug { "No se pudo guardar la butaca con id: ${butaca.id}" }
                return null
            }else return butaca
        }catch (e : Exception){
            logger.debug { "No se pudo guardar la butaca con id: ${butaca.id}: ${e.message}" }
            null
        }
    }

    override fun update(id: String, butaca: Butaca): Butaca? {
        TODO("Not yet implemented")
    }

    override fun delete(id: String): Butaca? {
        TODO("Not yet implemented")
    }

}
