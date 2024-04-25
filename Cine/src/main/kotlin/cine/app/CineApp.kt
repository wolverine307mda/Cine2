package cine.app

import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import org.example.butacas.models.Butaca
import org.example.butacas.models.Ocupamiento
import org.example.butacas.servicios.ButacaService
import org.example.cuenta.servicio.CuentaServicio
import org.example.productos.models.Producto
import org.example.productos.servicio.ProductoServicio
import org.example.ventas.servicio.VentaServicio
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDateTime

class CineApp : KoinComponent {
    // Inyección de dependencia para los servicios necesarios
    private val cuentaServicio: CuentaServicio by inject()
    private val ventaServicio: VentaServicio by inject()
    private val productoServicio: ProductoServicio by inject()
    private val butacaServicio : ButacaService by inject()
    var butacas: List<Butaca>? = null

    private fun sortButacas(): List<Butaca> {
        val sorted = mutableListOf<Butaca>()
        val letters = listOf('A','B','C','D','E')
        val numbers = listOf(1, 2, 3, 4, 5, 6, 7) // Changed to list of individual numbers

        for (number in numbers) {
            for (letter in letters) {
                butacas?.firstOrNull {
                    it.id[0] == letter && it.id.substring(1).toIntOrNull() == number
                }?.let {
                    sorted.add(it)
                }
            }
        }
        return sorted

    }

    var productos: List<Producto>? = null

    init {
        productoServicio
            .cargarTodosProductos()
            .onSuccess {
                it.forEach {
                    productoServicio.save(it)
                }
            }
    }

    fun iniciarCine() {
        println("\nBienvenido al cine\n")
        menuInicio()

    }


    private fun menuInicio() {
        var opcion: String?

        println("""¿Qué desea hacer? 
        |1. Ver butacas
        |2. Reservar butacas
        |3. Salir
        |4. Exportar las butacas""".trimMargin())

        do {
            print("Ingrese una opción:")
            opcion = readln()
            when (opcion) {
                "1" -> verEstadoDelCine()
                "2" -> buscarButacaParaReservar()
                "3" -> salir()
                "4" -> exportarButacas() //Exportar las butacas en un fichero JSON
                else -> println("Opción inválida")
            }
        } while (opcion !in listOf("1", "2", "3", "4"))
    }
    private fun menuIniciarSesion() {
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

    private fun exportarButacas() {
        val fechaRegex = "^\\d{4}/\\d{2}/\\d{2}\$".toRegex()
        var input : String
        println("Pon que fecha quieres consultar")
        input = readln()
        do {
            if (!(input.matches(fechaRegex) && checkDateValidity(input))){
                println("Fecha incorrecta")
                input = readln()
            }
        }while (!(input.matches(fechaRegex) && checkDateValidity(input)))
        val fechaCorrecta = input.split('/').map { it.toInt() }
        val fecha = LocalDateTime.of(fechaCorrecta[0],fechaCorrecta[1],fechaCorrecta[2],0,0,0)
        butacaServicio.exportAllToFile(date = fecha).onSuccess {
            println("Exportadas con éxito")
        }.onFailure {
            println("No se han podido exportar: ${it.message}")
        }
        menuInicio()
    }

    private fun checkDateValidity(input: String): Boolean {
        val fecha = input.split('/').map { it.toInt() }
        return if (fecha[1] !in (1..12) || fecha[2] !in (1..31)) false
        else true
    }

    private fun registrarse() {
        TODO("Not yet implemented")
    }
    private fun iniciarSesion() {
        TODO("Not yet implemented")
    }
    private fun buscarButacaParaReservar() {
        var fila: String
        var columna: Int
        actualizarButacas()
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
                    else -> {
                        println("La butaca $numeroButaca no está disponible para reservar.")
                        buscarButacaParaReservar()
                    }
                }
            }
            .onFailure {
                println("La butaca $numeroButaca no existe.")
                buscarButacaParaReservar()
            }
        menuInicio()
    }




    private fun reservarButaca(numeroButaca: String) {
        actualizarButacas()
        butacaServicio.findById(numeroButaca).onSuccess { butaca ->
            val butacaReservada = butaca.copy(ocupamiento = Ocupamiento.OCUPADA)
            butacaServicio.update(numeroButaca, butacaReservada).onSuccess { _ ->
                println("La butaca $numeroButaca ha sido reservada con éxito.")
            }.onFailure { error ->
                println("Error al reservar la butaca $numeroButaca: ${error.message}")
            }
        }.onFailure {
            println("La butaca $numeroButaca no existe.")
        }
    }
    private fun actualizarProductos() {
        val findAllResult = productoServicio.findAll()
        findAllResult.onSuccess { productosEncontrados ->
            productos = productosEncontrados
        }.onFailure { error ->
            println("Error al obtener los Productos: ${error.message}")
        }
    }
    private fun actualizarButacas() {
        // Manejo del resultado del servicio findAll()
        val findAllResult = butacaServicio.findAll()
        findAllResult.onSuccess { butacasEncontradas ->
            butacas = butacasEncontradas
        }.onFailure { error ->
            println("Error al obtener las butacas: ${error.message}")
        }
    }
    private fun verEstadoDelCine() {
        actualizarButacas()
        if (butacas != null) butacas = sortButacas()
        var contador = 0
        for (butaca in butacas!!) {
            when (butaca.estado.toString()) {
                "ACTIVA" -> when (butaca.ocupamiento.toString()){
                    "LIBRE" -> if (butaca.tipo.toString() == "VIP") print(" 🔵 ") else print(" 🟢 ")
                    "RESERVADA" -> print(" 🟡 ")
                    "OCUPADA" -> print(" 🟠 ")
                }
                else -> (" 🔴 ") // Agregamos un caso de que esté en mantenimiento o fuera de servicio
            }
            contador++
            if (contador % 5 == 0) {
                println()
            }
        }
        menuInicio()
    }
    private fun salir() {
        println("Gracias por su visita")
        // Finaliza la aplicación
        System.exit(0)
    }
}
