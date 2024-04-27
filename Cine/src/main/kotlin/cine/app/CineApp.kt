package cine.app

import com.github.michaelbull.result.fold
import com.github.michaelbull.result.onFailure
import com.github.michaelbull.result.onSuccess
import org.example.butacas.models.Butaca
import org.example.butacas.models.Ocupamiento
import org.example.butacas.servicios.ButacaService
import org.example.cuenta.models.Cuenta
import org.example.cuenta.servicio.CuentaServicio
import org.example.database.manager.logger
import org.example.productos.models.Producto
import org.example.productos.servicio.ProductoServicio
import org.example.ventas.models.LineaVenta
import org.example.ventas.models.Venta
import org.example.ventas.servicio.VentaServicio
import org.example.ventas.storage.VentaStorageHTMLImpl
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.time.LocalDateTime
import java.util.regex.Pattern

class CineApp : KoinComponent {
    // Inyección de dependencia para los servicios necesarios
    private val cuentaServicio: CuentaServicio by inject()
    private val ventaServicio: VentaServicio by inject()
    private val productoServicio: ProductoServicio by inject()
    private val butacaServicio : ButacaService by inject()
    var butacas: List<Butaca>? = null
    var productos: List<Producto>? = null
    private var inicioSesion:Boolean = false

    private lateinit var butacaTiket: Butaca
    private lateinit var cuentaTiket: Cuenta
    private var ProductoTiket: List<Producto>? = null
    private var productosReservados = 0



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
        |1. Reservar butacas
        |2. Exportar las butacas
        |3. Salir""".trimMargin())

        do {
            print("Ingrese una opción:")
            opcion = readln()
            when (opcion) {
                "1" -> buscarButacaParaReservar()
                "2" -> exportarButacas() //Exportar las butacas en un fichero JSON
                "3" -> salir()
                else -> println("Opción inválida")
            }
        } while (opcion !in listOf("1", "2", "3", "4"))
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

    private fun menuIniciarSesion() {
        var opcion: String? = null
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

    fun iniciarSesion() {
        // Patrón de expresión regular para el formato especificado
        val regex = Pattern.compile("[A-Za-z]{3}\\d{3}")

        var idIngresado: String
        var inicioSesionExitoso = false // Variable para controlar si se ha iniciado sesión con éxito

        do {
            // Solicitar al usuario que ingrese el ID
            print("Ingrese su ID (formato: LLLNNN): ")
            idIngresado = readlnOrNull()?.uppercase().toString()
            // Comprobar si el ID ingresado coincide con el patrón
            if (!regex.matcher(idIngresado).matches()) {
                println("El formato del ID no es válido.")
                continue
            }

            // Buscar la cuenta con el ID ingresado
            cuentaServicio.findById(idIngresado).fold(
                { cuenta ->
                    println("Inicio de sesión exitoso. ¡Bienvenido, User con ID=${cuenta.id}!")
                    cuentaTiket = cuenta
                    inicioSesionExitoso = true // Marcar que se ha iniciado sesión con éxito
                },
                { error ->
                    println("El ID ingresado no corresponde a ninguna cuenta.")
                    println("¿Desea registrarse? (S/N): ")
                    val respuesta = readlnOrNull()?.uppercase()
                    if (respuesta == "S") {
                        registrarse()
                    } else {
                        println("Volviendo a inicio de sesión...")
                    }
                }
            )
        } while (!inicioSesionExitoso) // Continuar el bucle hasta que se inicie sesión con éxito
    }

    private fun registrarse() {
        print("Ingrese su ID (formato: LLLNNN): ")
        var input = readln().uppercase()
        val regex = "[A-Za-z]{3}\\d{3}".toRegex()
        var success = false
        do {
            if (!input.matches(regex)){
                println("Ese ID no es válido, vuelva a intentarlo:")
                input = readln().uppercase()
            }else{
                cuentaServicio.save(Cuenta(input)).onSuccess {
                    println("Su cuenta se ha creado ")
                    success = true
                }.onFailure {
                    println("Ya existe una cuenta con ese nombre, vuelva a intentarlo:")
                    input = readln().uppercase()
                }
            }
        }while (!input.matches(regex) && !success)
    }


    private fun buscarButacaParaReservar() {
        actualizarButacas()
        verEstadoDelCine()
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
                        do {
                            menuIniciarSesion()
                        } while (inicioSesion)
                        reservarButaca(numeroButaca)
                        menuReservaProductos()
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

    private fun menuReservaProductos() {
        actualizarProductos()
        println("0. Volver al menú principal")
        println("Seleccione los productos que desea Comprar:")
        productos?.forEachIndexed { index, producto ->
            println("${index + 1}. ${producto.nombre} - ${producto.precio}€")
        }
        println("${productos?.size?.plus(1)}. Continuar sin Comprar productos")

        var opcion: Int
        do {
            print("Ingrese el número correspondiente al producto que desea reservar o 0 para volver al menú principal: ")
            opcion = readLine()?.toIntOrNull() ?: -1
            when {
                opcion == 0 -> menuInicio()
                opcion in 1..productos!!.size -> {
                    reservaProducto(opcion)
                }
                else -> println("Opción inválida")
            }
        } while (opcion !in 0..productos!!.size + 1)
    }


    private fun reservaProducto(opcion: Int) {
        val productoSeleccionado = productos!![opcion - 1]
        println("Ha seleccionado: ${productoSeleccionado.nombre}")

        productoServicio.findById(productoSeleccionado.id).onSuccess { producto ->
            val productoReservado = producto.copy(stock = producto.stock - 1)
            productoServicio.update(productoSeleccionado.id, productoReservado).onSuccess { _ ->
                println("El producto ${productoSeleccionado.nombre} ha sido reservado con éxito.")
                ProductoTiket = ProductoTiket.orEmpty() + productoReservado // Agregar el producto reservado a ProductoTiket
                productosReservados++
            }.onFailure { error ->
                println("Error al reservar el producto ${productoSeleccionado.nombre}: ${error.message}")
            }
        }.onFailure {
            println("El producto ${productoSeleccionado.nombre} no existe.")
        }

        if (productosReservados < 3) {
            println("Aun puede seleccionar ${3 - productosReservados}")
            var respuesta: String?
            do {
                print("Desea seleccionar más productos? (S/N): ")
                respuesta = readLine()?.uppercase()
                when (respuesta) {
                    "S" -> menuReservaProductos()
                    "N" -> {
                        println("Gracias por su compra")
                        crearVenta()
                    }
                    else -> println("Respuesta inválida, por favor ingrese S o N")
                }
            } while (respuesta != "S" && respuesta != "N")
        }

    }

    private fun crearVenta() {
        val cliente = cuentaTiket
        val butaca = butacaTiket

        // Crear las líneas de venta
        val lineasVenta = ProductoTiket!!.map { producto ->
            LineaVenta(producto = producto, cantidad = 1, precio = producto.precio)
        }

        // Crear la venta
        val venta = Venta(cliente = cliente, butaca = butaca!!, lineasVenta = lineasVenta)

        // Guardar la venta utilizando el repositorio de ventas
        ventaServicio.save(venta)?.let {
            println("Venta creada con éxito.")

            // Exportar la venta a HTML
            val ventaStorage = VentaStorageHTMLImpl()
            ventaStorage.exportar(venta).fold(
                success = { println("Venta exportada a HTML con éxito.") },
                failure = { error -> println("Error al exportar la venta a HTML: ${error.message}") }
            )
        }
    }


    private fun reservarButaca(numeroButaca: String) {
        actualizarButacas()
        butacaServicio.findById(numeroButaca).onSuccess { butaca ->
            val butacaReservada = butaca.copy(ocupamiento = Ocupamiento.OCUPADA)
            butacaServicio.update(numeroButaca, butacaReservada).onSuccess { _ ->
                println("La butaca $numeroButaca ha sido reservada con éxito.")
                butacaTiket = butacaReservada
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
        var columna = 1
        println("""
        |A continuación se mostraran las butacas por colores:
        |   🔵-> VIP.
        |   🟢-> NORMALES.
        |   🟡-> RESERVADAS.
        |   🟠-> OCUPADAS.
        |   🔴-> FUERA DE SERVICIO O MANTENIMIENTO.
        |   
    """.trimMargin())
        print("   A   B   C   D   E")
        for ((contador, butaca) in butacas!!.withIndex()) {
            if (contador % 5 == 0) {
                println()
                print(columna)
                columna++
            }
            when (butaca.estado.toString()) {
                "ACTIVA" -> when (butaca.ocupamiento.toString()){
                    "LIBRE" -> if (butaca.tipo.toString() == "VIP") print(" 🔵 ") else print(" 🟢 ")
                    "RESERVADA" -> print(" 🟡 ")
                    "OCUPADA" -> print(" 🟠 ")
                }
                else -> (" 🔴 ") // Agregamos un caso de que esté en mantenimiento o fuera de servicio
            }
        }
        println()
        println()
    }
    private fun salir() {
        println("Gracias por su visita")
        // Finaliza la aplicación
        System.exit(0)
    }
}