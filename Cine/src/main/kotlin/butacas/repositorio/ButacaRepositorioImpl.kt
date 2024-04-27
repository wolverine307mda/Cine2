package org.example.butacas.repositorio

import org.example.butacas.mappers.toButaca
import org.example.butacas.models.Butaca
import org.example.cuenta.mappers.toLong
import org.example.database.manager.SqlDelightManager
import org.example.database.manager.logger
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

    override fun findAllBasedOnDate(date: LocalDateTime): List<Butaca> {
        logger.debug { "Buscando todas las butacas en la base de datos antes de ${date.dayOfMonth}/${date.monthValue}/${date.year}" }
        if (db.countButacasBasedOnDate(date.toString()).executeAsOne() > 0){
            return db.getButacasBasedOnDate(date.toString()).executeAsList().map {
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

    override fun save(butaca: Butaca, ignoreKey : Boolean): Butaca? {
        logger.debug { "Buscado la butaca con id: ${butaca.id}" }
        if (ignoreKey || findById(butaca.id) == null){
            db.insertButaca(
                id = butaca.id.uppercase(),
                tipo = butaca.tipo!!.name,
                estado = butaca.estado!!.name,
                ocupamiento = butaca.ocupamiento!!.name,
                createdAt = butaca.createdAt.toString(),
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
            val nuevaButaca = butaca.copy(
                id = id,
                estado = butaca.estado,
                ocupamiento = butaca.ocupamiento,
                updatedAt = LocalDateTime.now(),
                isDeleted = butaca.isDeleted
            )
            save(nuevaButaca, true)?.let { return nuevaButaca }
            return null
        }
        return null
    }

    override fun delete(id: String): Butaca? {
        logger.debug { "Borrando butaca con id: $id" }
        findById(id)?.let {
            val nuevaButaca = it.copy(
                id = id,
                estado = it.estado,
                ocupamiento = it.ocupamiento,
                updatedAt = LocalDateTime.now(),
                isDeleted = true
            )
            save(nuevaButaca, true)?.let { return nuevaButaca }
            return null
        }
        return null
    }

    override fun findByIdAndDate(id: String, date: LocalDateTime): Butaca? {
        logger.debug { "Encontrando la butaca con id= $id en ${date.dayOfMonth}/${date.monthValue}/${date.year} ${date.hour}:${date.minute}:${date.second}" }
        if(db.butacaExistsOnACertainDate(id,date.toString()).executeAsOne()){
            return db.getButacaBasedOnIdAndDate(id,date.toString()).executeAsOne().toButaca()
        }
        return null
    }

}
