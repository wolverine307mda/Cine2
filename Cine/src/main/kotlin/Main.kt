package org.example

import cine.app.CineApp
import org.example.cuenta.mappers.toLong
import org.example.database.manager.SqlDelightManager
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.koin.core.context.GlobalContext.startKoin
import org.koin.fileProperties
import org.koin.ksp.generated.defaultModule
import org.koin.test.verify.verify

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
@OptIn(KoinExperimentalAPI::class)
fun main() {
    startKoin{
        printLogger()
        // Leemos las propiedades de un fichero
        fileProperties("/config.properties") // Por defecto busca en src/main/resources/config.properties, pero puede ser otro fichero si se lo pasas como parametro
        // declara modulos de inyección de dependencias, pero lo verificamos antes de inyectarlos
        // para asegurarnos que todo está correcto y no nos de errores
        defaultModule.verify(
            extraTypes = listOf(
                Boolean::class,
                Int::class
            )
        ) // Verificamos que los módulos están bien configurados antes de inyectarlos (ver test!)
        modules(defaultModule)
    }
    val dummy = Dummy()
    dummy.run()
}

class Dummy : KoinComponent {
    fun run(){
        val cineApp = CineApp()
        cineApp.iniciarCine()
    }
}
