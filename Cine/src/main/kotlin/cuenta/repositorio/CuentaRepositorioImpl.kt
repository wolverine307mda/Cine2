package org.example.cuenta.repositorio

import org.example.cuenta.dto.CuentaDTO
import org.example.cuenta.mappers.toCuenta
import org.example.cuenta.mappers.toCuentaDto
import org.example.cuenta.models.Cuenta
import org.example.database.manager.DataBaseManager
import org.example.database.manager.logger
import org.koin.core.annotation.Singleton
import java.time.LocalDateTime

@Singleton
class CuentaRepositorioImpl(
    val dataBaseManager: DataBaseManager
) : CuentaRepositorio {

    override fun findAll(): List<Cuenta> {
        logger.debug { "Buscando todas las personas" }
        try {
            val personas = mutableListOf<Cuenta>()
            dataBaseManager.use { db ->
                val sql = "SELECT * FROM CuentaEntity"
                val result = db.connection?.prepareStatement(sql)!!.executeQuery()
                while (result.next()) {
                    val persona = CuentaDTO(
                        id = result.getString("id"),
                        isDeleted = result.getInt("isDeleted"),
                        createdAt = result.getString("createdAt"),
                        updatedAt = result.getString("updatedAt")
                    ).toCuenta()
                    personas.add(persona)
                }
            }
            return personas
        } catch (e: Exception) {
            logger.error { "Hubo un error al cargar las cuentas" }
            return emptyList()
        }
    }

    override fun findById(id: String): Cuenta? {
        logger.debug { "Buscando cliente por id $id" }
        try {
            var cuenta: Cuenta? = null
            dataBaseManager.use { db ->
                val sql = "SELECT * FROM CuentaEntity WHERE id = ?"
                val statement = db.connection?.prepareStatement(sql)!!
                statement.setString(1, id)
                val result = statement.executeQuery()
                if (result.next()) {
                    cuenta = CuentaDTO(
                        id = result.getString("id"),
                        updatedAt = result.getString("updatedAt"),
                        createdAt = result.getString("createdAt"),
                        isDeleted = result.getInt("isDeleted")
                    ).toCuenta()
                }
            }
            return cuenta //Si no ha fallado
        } catch (e: Exception) {
            logger.error { "Error al encontrar la cuenta con id: $id" }
            return null //Si ha fallado
        }
    }

    override fun save(cuenta: Cuenta): Cuenta? {
        logger.debug { "Guardando cuenta con id: ${cuenta.id}" }
        try {
            if (findById(cuenta.id) == null){
                var cuentaDTO = cuenta.toCuentaDto()
                val timeStamp = LocalDateTime.now()
                dataBaseManager.use { db ->
                    val sql =
                        "INSERT INTO CuentaEntity (id, isDeleted, createdAt, updatedAt) VALUES (?, ?, ?, ?)"
                    val statement = db.connection?.prepareStatement(sql)!!
                    statement.setString(1, cuentaDTO.id)
                    statement.setInt(2, cuentaDTO.isDeleted)
                    statement.setString(3, cuentaDTO.createdAt)
                    statement.setString(4, timeStamp.toString())
                    statement.executeUpdate()
                }
                return cuenta //Si no existe y no falla
            }else return null //Si ya existe en la base de datos
        } catch (e: Exception) {
            logger.error { "Error al guardar la cuenta con id: ${cuenta.id}" }
            return null //Si falla
        }
    }

    override fun update(id: String, cuenta: Cuenta): Cuenta? {
        logger.debug { "Actualizando cuenta con id: $id" }
        findById(id)?.let {
            try {
                if (findById(cuenta.id) == null){
                    var cuentaDTO = cuenta.toCuentaDto()
                    val timeStamp = LocalDateTime.now()
                    dataBaseManager.use { db ->
                        val sql =
                            "INSERT INTO CuentaEntity (id, isDeleted, createdAt, updatedAt) VALUES (?, ?, ?, ?)"
                        val statement = db.connection?.prepareStatement(sql)!!
                        statement.setString(1, cuentaDTO.id)
                        statement.setInt(2, cuentaDTO.isDeleted)
                        statement.setString(3, cuentaDTO.createdAt)
                        statement.setString(4, timeStamp.toString())
                        statement.executeUpdate()
                    }
                    return cuenta //Si no existe y no falla
                }else return null //Si ya existe en la base de datos
            } catch (e: Exception) {
                logger.error { "Error al actualizar la cuenta con id: $id" }
                return null //Si falla
            }
        }
        return null //Si no existe
    }

    override fun delete(id: String): Cuenta? {
        logger.debug { "Borrando la cuenta con id $id" }
        findById(id)?.let {
            try {
                var cuentaDTO = it.toCuentaDto()
                val timeStamp = LocalDateTime.now()
                dataBaseManager.use { db ->
                    val sql =
                        "INSERT INTO CuentaEntity (id, isDeleted, createdAt, updatedAt) VALUES (?, ?, ?, ?)"
                    val statement = db.connection?.prepareStatement(sql)!!
                    statement.setString(1, cuentaDTO.id)
                    statement.setInt(2, 1)
                    statement.setString(3, cuentaDTO.createdAt)
                    statement.setString(4, timeStamp.toString())
                    statement.executeUpdate()
                }
                return it //Si existe y no ha fallado
            } catch (e: Exception) {
                logger.error { "Error al borrar la cuenta con id: $id" }
                return null //Si falla
            }
        }
        return null //Si no existe
    }
}