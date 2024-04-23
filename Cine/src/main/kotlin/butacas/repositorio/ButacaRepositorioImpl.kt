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
        logger.debug { "Obteniendo todos los productos" }
        return db.getAllButacas()
            .executeAsList()
            .map { it.toButaca() }
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
            isDeleted = butaca.isDeleted.toLong()
        )
        return findById(butaca.id).also {
            it?.let {
            logger.debug { "Añadida la butaca con id: ${butaca.id}" }
            }
        }
    }

    override fun update(id: String, butaca: Butaca): Butaca? {
        val existingButaca = findById(id)
        if (existingButaca != null) {
            db.updateButaca(
                tipo = butaca.tipo!!.name,
                estado = butaca.estado!!.name,
                ocupamiento = butaca.ocupamiento!!.name,
                updatedAt = LocalDateTime.now().toString(),
                id = id.uppercase()
            )
            return findById(id)
        } else {
            logger.debug { "La butaca con id $id no existe, no se puede actualizar" }
            return null
        }
    }

    override fun delete(id: String): Butaca? {
        TODO("Not yet implemented")
    }

}
