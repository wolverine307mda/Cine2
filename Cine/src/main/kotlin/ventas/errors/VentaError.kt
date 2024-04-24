package org.example.ventas.errors

sealed class VentaError(var message : String) {
    class VentaInvalida(message : String) : VentaError(message)
    class VentaStorageError(message: String) : VentaError(message)
}