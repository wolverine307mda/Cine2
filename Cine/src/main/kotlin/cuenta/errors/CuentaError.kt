package org.example.butacas.errors

sealed class CuentaError (val message : String) {
    class CuentaInvalida(message: String) : CuentaError(message)
    class CuentaStorageError(message: String) : CuentaError(message)
    class CuentaNotFoundError(message: String) : CuentaError(message)
}