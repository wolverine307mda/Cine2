package org.example.butacas.repositorio

import org.example.butacas.models.Butaca

interface ButacaRepositorio {
    fun findAll(): List<Butaca>
    fun findById(id: String): Butaca?
    fun save(butaca: Butaca): Butaca?
    fun update(id: String, butaca: Butaca): Butaca?
    fun delete(id: String): Butaca?
}