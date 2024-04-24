package org.example.butacas.errors


sealed class ProductoError(val message: String) {
    class ProductoStorageError(message: String) : ProductoError(message)
    class ProductoInvalido(message: String) : ProductoError(message)
    class ProductoNotFoundError(message: String) : ProductoError(message)
}