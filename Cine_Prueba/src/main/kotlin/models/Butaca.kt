package org.example.models

class Butaca (
    val id: Int,
    val fila: String,
    val columna: Int,
    val tipo: String, // Normal o VIP
    val estado: String // Libre, Ocupada o Fuera de Servicio
)