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
        try {
            return db.getButacaById(id).executeAsOne().toButaca()
        }catch (e : Exception){
            return null //En caso de que no la encuentre
        }
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
        return try { //Porque puede dar una excepcionn si no hay nada en la base de datos
            if (db.selectLastButacaInserted().executeAsOne().id != butaca.id){
                //Si la ultima butaca metida no coincide con la que acabamos de intentar meter
                logger.debug { "No se pudo guardar la butaca con id: ${butaca.id}" }
                return null
            }else return butaca //Si la ha metido correctamente
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
