package cine.app

import org.example.butacas.models.Ocupamiento
import org.example.butacas.models.Tipo
import org.example.butacas.repositorio.ButacaRepositorioImpl
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
    private val butacaRepositorio: ButacaRepositorioImpl by inject()

    fun inicioDeVenta() {

        println()
        println()

        println("Bienvenido al cine")
        // Mostrar mensaje de bienvenida y opciones al usuario
        menuCine()
    }

    private fun menuCine() {
        println(
            """¿Qué desea hacer? 
            |1. Ver butacas
            |2. Reservar butacas
            |3. Salir""".trimMargin()
        )
        // Solicitar al usuario que ingrese una opción
        print("Ingrese una opción:")
        // Leer la opción ingresada por el usuario
        when (readLine()) {
            "1" -> verButacas() // Mostrar butacas
            "2" -> reservarButacas() // Reservar butacas
            "3" -> salir() // Salir de la aplicación
            else -> println("Opción inválida")
        }
    }

    // Función para mostrar las butacas al usuario
    private fun verButacas() {
        // Obtener todas las butacas de la base de datos
        val butacasList = butacaRepositorio.findAll()

        // Crear una lista de símbolos para representar el estado de cada butaca
        val listaSimbolos = butacasList.map { butaca ->
            // Determinar el símbolo para representar el estado de la butaca
            when (butaca.ocupamiento) {
                Ocupamiento.LIBRE -> if (butaca.tipo == Tipo.VIP) " 🟢 " else " 🟡 "
                Ocupamiento.RESERVADA -> " 🟠 "
                Ocupamiento.OCUPADA -> " 🔴 "
                else -> "   " // Agregamos un caso para el valor nulo o desconocido
            }
        }

        // Imprimir la lista de símbolos como una matriz
        listaSimbolos.chunked(7).forEach { fila ->
            println(fila.joinToString(""))
        }

        menuCine()
    }

    private fun reservarButacas() {
        var fila: String
        var columna: Int

        // ingrese el número de fila (A-E)
        do {
            print("Ingrese el número de fila (A-E): ")
            fila = readLine()?.toUpperCase() ?: ""
        } while (fila !in "ABCDE")

        // ingrese el número de columna (1-7)
        do {
            print("Ingrese el número de columna (1-7): ")
            val input = readLine()
            columna = input?.toIntOrNull() ?: 0
        } while (columna !in 1..7)

        // juntamos la fila y la columna
        val numeroButaca = "$fila$columna"

        // Buscar la butaca
        val butaca = butacaRepositorio.findById(numeroButaca)
        if (butaca != null && butaca.ocupamiento == Ocupamiento.LIBRE) {
            // Reservamos la butaca
            println("Butaca $numeroButaca reservada exitosamente.")
        } else {
            println("La butaca $numeroButaca no está disponible para reservar.")
        }
        menuCine()
    }

    private fun salir() {
        println("Gracias por su visita")
        // Finaliza la aplicación
        System.exit(0)
    }
}
