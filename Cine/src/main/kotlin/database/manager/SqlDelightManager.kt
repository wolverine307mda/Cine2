package org.example.database.manager

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import database.DatabaseQueries
import org.cine.database.AppDatabase
import org.example.butacas.storage.ButacaStorage
import org.example.butacas.storage.ButacaStorageCSVImpl
import org.example.butacas.validator.ButacaValidator
import org.example.config.Config
import org.example.cuenta.mappers.toLong
import org.koin.core.annotation.Singleton
import org.lighthousegames.logging.logging
import java.io.File

val logger = logging()

@Singleton
class SqlDelightManager(
    private val butacaStorageCSVImpl: ButacaStorageCSVImpl,
    private val butacaValidator: ButacaValidator,
    private val config : Config
) {
    private val databaseUrl: String = config.databaseUrl
    private val databaseInitData: Boolean = config.databaseInitData
    private val databaseInMemory: Boolean = config.databaseInMemory
    val databaseQueries: DatabaseQueries = initQueries()

    init {
        logger.debug { "Inicializando el gestor de Bases de Datos con SQLDelight" }
        initialize()
    }

    /**
     * Crea la base de datos en memoria o en fichero dependiendo de lo que ponga en
     * @return un objeto DatabaseQueries que es utilizado por otras clases para
     * utilizar las funciones creadas automaticamente por SQLDelight a partir de las
     * que están presentes en el fichero Database.sq
     */
    private fun initQueries(): DatabaseQueries {

        return if (databaseInMemory) {
            logger.debug { "SqlDeLightClient - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "SqlDeLightClient - File: ${databaseUrl}" }
            JdbcSqliteDriver(databaseUrl)
        }.let { driver ->
            // Creamos la base de datos
            logger.debug { "Creando Tablas (si es necesario)" }
            AppDatabase.Schema.create(driver)
            AppDatabase(driver)
        }.databaseQueries

    }

    /**
     * Borra todos los datos existentes en la base datos y carga los de ejemplo
     * @see removeAllData
     * @see demoButacas
     */
    fun initialize() {
        if (databaseInitData) {
            removeAllData()
            demoButacas()
        }
    }
    /**
     * Es una funcion que lee un archivo que está en la carpeta resources.
     * Con la ayuda de ButacaStorage lo transforma en una lista de butacas válidas y las
     * mete en la base de datos.
     * Dependiendo de que tipo de fichero querramos leer
     * también cambiará la implementacion de ButacaStorage que se inyecta a SqlDelightManager.
     * El nombre del archivo se encuentra en config.properties y se guarda
     * en la clase Config con el nombre butacaSampleFile
     * @see ButacaStorage
     * @see Config
     */
    private fun demoButacas() {
        logger.debug { "Importando datos de butacas" }
        val url = ClassLoader.getSystemResource(config.butacaSampleFile)
        if (url != null){
            butacaStorageCSVImpl
                .cargar(File(url.toURI()))
                .onSuccess {
                    it.forEach {
                        butacaValidator.validate(it)
                            .onSuccess {  databaseQueries.insertButaca(
                                id = it.id.uppercase(),
                                tipo = it.tipo!!.name,
                                estado = it.estado!!.name,
                                ocupamiento = it.ocupamiento!!.name,
                                createdAt = it.updatedAt.toString(),
                                updatedAt = it.updatedAt.toString(),
                                isDeleted = it.isDeleted.toLong()
                            )
                                logger.debug { "Añadida la butaca con id: ${it.id}" }
                            }
                            .onFailure { logger.debug { it.message } }
                    }
                }
        }else logger.debug { "No se ha encontrado el fichero ${config.butacaSampleFile}" }
    }


    /**
     * Borra todos los datos de la base de datos con la ayuda de las funciones que crea
     * SqlDelight dentro del fichero Database.sq
     */
    private fun removeAllData() {
        logger.debug { "Borrando todo el data existente en la base de datos" }
        databaseQueries.transaction {
            databaseQueries.removeAllButacas()
            databaseQueries.removeAllCuentas()
            databaseQueries.removeAllProductos()
            databaseQueries.removeAllVentas()
        }
    }
}