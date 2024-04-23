package org.example.butacas.errors

sealed class ButacaError (val message : String){
    class ButacaInvalida(message: String) : ButacaError(message)
    class ButacaStorageError(message: String) : ButacaError(message)
    class ButacaNotFoundError(message: String) : ButacaError(message)
}