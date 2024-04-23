package cine.app

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import org.example.butacas.models.Butaca
import org.example.butacas.models.Ocupamiento
import org.example.butacas.servicios.ButacaService
import org.example.cuenta.servicio.CuentaServicio
import org.example.productos.servicio.ProductoServicio
import org.example.ventas.servicio.VentaServicio
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class CineApp : KoinComponent {
    // Inyección de dependencia para los servicios necesarios
    private val cuentaServicio: CuentaServicio by inject()
    private val ventaServicio: VentaServicio by inject()
    private val productoServicio: ProductoServicio by inject()
    private val butacaServicio : ButacaService by inject()

    fun iniciarCine() {
        println()
        println("Bienvenido al cine")
        println()

        // iniciamos el servicio de butacas
        butacaServicio.findAll()
            .onSuccess { butacas ->
                //butacas.forEach { println(it) } // ACTIVAR SI DESEA VER LAS BUTACAS Y SUS ESTADOS
                menuInicio(butacas)
            }
    }

    private fun menuInicio(butacas: List<Butaca>) {
        var opcion: String?
        println("""¿Qué desea hacer? 
        |1. Ver butacas
        |2. Reservar butacas
        |3. Salir""".trimMargin())

        do {
            // Solicitar al usuario que ingrese una opción
            print("Ingrese una opción:")
            // Leer la opción ingresada por el usuario
            opcion = readln()
            when (opcion) {
                "1" -> verEstadoDelCine(butacas) // Mostrar butacas
                "2" -> menuIniciarSesion(butacas) // Reservar butacas
                "3" -> salir() // Salir de la aplicación
                else -> println("Opción inválida")
            }
        } while (opcion !in listOf("1", "2", "3"))
    }
    private fun menuIniciarSesion(butacas: List<Butaca>) {
        var opcion: String?
        println("""¿Qué desea hacer? 
        |1. Iniciar sesión
        |2. Registrarse
        |3. Salir""".trimMargin())

        do {
            // Solicitar al usuario que ingrese una opción
            print("Ingrese una opción:")
            // Leer la opción ingresada por el usuario
            opcion = readln()
            when (opcion) {
                "1" -> iniciarSesion() // Iniciar sesión
                "2" -> registrarse() // Registrarse
                "3" -> salir() // Salir de la aplicación
                else -> println("Opción inválida")
            }
        } while (opcion !in listOf("1", "2", "3"))
    }

    private fun registrarse() {
        TODO("Not yet implemented")
    }
    private fun iniciarSesion() {
        TODO("Not yet implemented")
    }

    // Función para mostrar las butacas
    private fun verEstadoDelCine(butacas: List<Butaca>) {
        var contador = 0
        for (butaca in butacas) {
            when (butaca.ocupamiento.toString()) {
                "LIBRE" -> if (butaca.tipo.toString() == "VIP") print(" 🔵 ") else print(" 🟢 ")
                "RESERVADA" -> print(" 🟡 ")
                "OCUPADA" -> print(" 🟠 ")
                else -> (" 🔴 ") // Agregamos un caso para el valor nulo o desconocido
            }
            contador++
            if (contador % 5 == 0) {
                println()
            }
        }
    }

    //
    private fun BuscarButacaParaReservar(butacas: List<Butaca>) {
        var fila: String
        var columna: Int

        // ingrese el número de fila (A-E)
        do {
            print("Ingrese el número de fila (A-E): ")
            fila = readlnOrNull()?.uppercase() ?: ""
        } while (fila !in "ABCDE")
        // ingrese el número de columna (1-7)
        do {
            print("Ingrese el número de columna (1-7): ")
            val input = readlnOrNull()
            columna = input?.toIntOrNull() ?: 0
        } while (columna !in 1..7)
        // juntamos la fila y la columna
        val numeroButaca = "$fila$columna"

        // Buscar la butaca
        butacaServicio
            .findById(numeroButaca)
            .onSuccess { butaca ->
                when (butaca.ocupamiento) {
                    Ocupamiento.LIBRE -> {
                        // Reservamos la butaca
                        reservarButaca(numeroButaca)
                    }
                    else -> println("La butaca $numeroButaca no está disponible para reservar.")
                }
            }
            .onFailure {
                println("La butaca $numeroButaca no existe.")
            }
        menuInicio(butacas)
    }

    private fun reservarButaca(numeroButaca: String) {
        // Actualizar el estado de la butaca a RESERVADA
    }
    private fun salir() {
        println("Gracias por su visita")
        // Finaliza la aplicación
        System.exit(0)
    }
}
