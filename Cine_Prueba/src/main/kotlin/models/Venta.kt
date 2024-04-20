package org.example.models

class Venta(
    val id: Int,
    val idButaca: Int,
    val idSocio: String,
    val fechaCompra: String,
    val complementos: List<Complemento> = listOf()
)