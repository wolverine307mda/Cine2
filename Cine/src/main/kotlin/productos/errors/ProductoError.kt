package org.example.butacas.errors


sealed class ProductoError(message: String) {
    class ProductoStorageError(message: String) : ProductoError(message)
    class ProductoInvalido(message: String) : ProductoError(message)
}