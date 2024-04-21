package org.example.config

import org.example.database.manager.logger
import org.koin.core.annotation.Property
import org.koin.core.annotation.Singleton
import java.nio.file.Files
import kotlin.io.path.Path

@Singleton
class Config(
    @Property("database.url")
    _databaseUrl: String = "jdbc:sqlite:productos.db",
    @Property("database.init.tables")
    _databaseInitTables: String = "true",
    @Property("database.init.data")
    _databaseInitData: String = "true",
    @Property("database.inmemory")
    _databaseInMemory: String = "true",
    @Property("storage.data")
    _storageData: String = "data",
    @Property("butacas.butacaSampleFile")
    _butacaSampleFile : String = "butacas.csv"
) {
    val databaseUrl: String = _databaseUrl
    val databaseInitTables: Boolean = _databaseInitTables.toBoolean()
    val databaseInitData: Boolean = _databaseInitData.toBoolean()
    val databaseInMemory: Boolean = _databaseInMemory.toBoolean()
    val storageData: String = _storageData
    val butacaSampleFile : String = _butacaSampleFile

    init {
        try {
            logger.debug { "Cargando configuración" }
            // crear el directorio si no existe
            Files.createDirectories(Path(storageData))

        } catch (e: Exception) {
            logger.error { "Error cargando configuración: ${e.message}" }
        }

    }
}