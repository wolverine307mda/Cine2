package org.example

import SqlDeLightManager


fun main() {
    val cinemaSqlDeLightManager = SqlDeLightManager()

    // Realizar operaciones con la base de datos del cine
    cinemaSqlDeLightManager.databaseQueries.transaction {
        // Por ejemplo, obtener todas las butacas disponibles
        val butacasDisponibles = cinemaSqlDeLightManager.databaseQueries.getButacasDisponibles()
        println("Butacas disponibles: $butacasDisponibles")

        // Realizar otras operaciones como comprar entradas, obtener recaudación, etc.
    }
}
