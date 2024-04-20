import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.lighthousegames.logging.logging


class SqlDeLightManager(
    private val _databaseUrl: String = "jdbc:sqlite:cine.db",
    private val _databaseInitData: String = "true",
    private val _databaseInMemory: String = "true"
) {
    private val databaseUrl: String = _databaseUrl
    private val databaseInitData: Boolean = _databaseInitData.toBoolean()
    private val databaseInMemory: Boolean = _databaseInMemory.toBoolean()
    private val logger = logging()

    val databaseQueries: DatabaseQueries = initQueries()

    init {
        logger.debug { "Inicializando el gestor de Base de Datos para el cine con SQLDelight" }
        // Inicializamos datos de ejemplo, si se ha configurado
        initialize()
    }

    private fun initQueries(): DatabaseQueries {
        return if (databaseInMemory) {
            logger.debug { "CinemaSqlDeLightManager - InMemory" }
            JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
        } else {
            logger.debug { "CinemaSqlDeLightManager - File: ${databaseUrl}" }
            JdbcSqliteDriver(databaseUrl)
        }.let { driver ->
            // Creamos la base de datos
            logger.debug { "Creando Tablas (si es necesario)" }
            CinemaAppDatabase.Schema.create(driver)
            CinemaAppDatabase(driver)
        }.DatabaseQueries
    }

    fun initialize() {
        if (databaseInitData) {
            removeAllData()
            initExampleData()
        }
    }

    private fun initExampleData() {
        logger.debug { "Iniciando datos de ejemplo para el cine" }
        databaseQueries.transaction {
            initExampleButacas()
            initExampleComplementos()
            // Agregar más inicializaciones de datos de ejemplo si es necesario
        }
    }

    private fun initExampleButacas() {
        logger.debug { "Datos de ejemplo de butacas" }
        // Aquí inicializamos datos de ejemplo para las butacas del cine
    }

    private fun initExampleComplementos() {
        logger.debug { "Datos de ejemplo de complementos" }
        // Aquí inicializamos datos de ejemplo para los complementos del cine
    }

    // Limpiamos las tablas
    private fun removeAllData() {
        logger.debug { "CinemaSqlDeLightManager.removeAllData()" }
        databaseQueries.transaction {
            // Aquí eliminamos todos los datos de las tablas relevantes para reiniciar el estado del cine
        }
    }
}
